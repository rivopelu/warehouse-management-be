package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestAddStock;
import com.warehouse.app.entities.ProductVariantUnit;
import com.warehouse.app.entities.Stock;
import com.warehouse.app.entities.VariantProduct;
import com.warehouse.app.entities.Warehouse;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.StockRepository;
import com.warehouse.app.repositories.VariantProductRepository;
import com.warehouse.app.repositories.WarehouseRepository;
import com.warehouse.app.services.AccountService;
import com.warehouse.app.services.TransactionService;
import com.warehouse.app.utilities.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final WarehouseRepository warehouseRepository;
    private final VariantProductRepository variantProductRepository;
    private final StockRepository stockRepository;
    private final AccountService accountService;

    private final Logger logger = Logger.getLogger(TransactionServiceImpl.class.getName());

    @Override
    public String addStockVariant(RequestAddStock req) {
        Warehouse warehouse = warehouseRepository.findById(req.getWarehouseId())
                .orElseThrow(() -> new NotFoundException("Warehouse not found"));
        VariantProduct variantProduct = variantProductRepository.findById(req.getVariantId())
                .orElseThrow(() -> new NotFoundException("Variant not found"));

        try {
            List<ProductVariantUnit> unitTypeList = new ArrayList<>(variantProduct.getVariantUnits());
            unitTypeList.sort(Comparator.comparingInt(ProductVariantUnit::getCount));

            Map<String, Integer> newStockMap = new LinkedHashMap<>();

            ProductVariantUnit mainUnit = unitTypeList.getLast();
            int mainUnitQuantity = req.getQuantity();
            newStockMap.put(mainUnit.getUnit().getId(), mainUnitQuantity);

            for (int i = unitTypeList.size() - 1; i >= 0; i--) {
                ProductVariantUnit currentUnit = unitTypeList.get(i);

                if (currentUnit.getParentUnit() != null) {
                    String parentUnitId = currentUnit.getParentUnit().getId();
                    int parentQuantity = newStockMap.getOrDefault(parentUnitId, 0);
                    int convertedQuantity = parentQuantity * currentUnit.getQuantity();
                    newStockMap.put(currentUnit.getUnit().getId(), convertedQuantity);
                }
            }

            System.out.println(newStockMap);

            for (Map.Entry<String, Integer> entry : newStockMap.entrySet()) {
                if (entry.getValue() < 0) {
                    throw new BadRequestException("Negative stock detected for unit ID: " + entry.getKey());
                }
            }

            List<Stock> existingStocks = stockRepository.findByVariantProductIdAndWarehouseId(
                    req.getVariantId(), req.getWarehouseId());

            Map<String, Stock> existingStockMap = existingStocks.stream()
                    .collect(Collectors.toMap(
                            stock -> stock.getUnitType().getId(),
                            stock -> stock));

            List<Stock> stocksToSave = new ArrayList<>();

            for (ProductVariantUnit unit : unitTypeList) {
                String unitId = unit.getUnit().getId();
                int stockQty = newStockMap.getOrDefault(unitId, 0);

                logger.info("Processing unit ID: {}, quantity: {}");

                if (existingStockMap.containsKey(unitId)) {
                    Stock existingStock = existingStockMap.get(unitId);
                    existingStock.setQuantity(existingStock.getQuantity() + stockQty);
                    EntityUtils.updated(existingStock, accountService.getCurrentAccountId());
                    stocksToSave.add(existingStock);
                } else {
                    Stock newStock = Stock.builder()
                            .warehouse(warehouse)
                            .variantProduct(variantProduct)
                            .unitType(unit.getUnit())
                            .quantity(stockQty)
                            .build();
                    EntityUtils.created(newStock, accountService.getCurrentAccountId());
                    stocksToSave.add(newStock);
                }
            }

            if (!stocksToSave.isEmpty()) {
                stockRepository.saveAll(stocksToSave);
            }

            return "SUCCESS";
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
