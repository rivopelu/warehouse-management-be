package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestAddStock;
import com.warehouse.app.entities.VariantProduct;
import com.warehouse.app.entities.Warehouse;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.repositories.VariantProductRepository;
import com.warehouse.app.repositories.WarehouseRepository;
import com.warehouse.app.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final WarehouseRepository warehouseRepository;
    private final VariantProductRepository variantProductRepository;

    @Override
    public String addStockVariant(RequestAddStock req) {

        Warehouse warehouse = warehouseRepository.findById(req.getWarehouseId()).orElseThrow(() -> new NotFoundException("warehouse not found"));
        VariantProduct variantProduct = variantProductRepository.findById(req.getVariantId()).orElseThrow(() -> new NotFoundException("variant not found"));




        return "SUCCESS";
    }
}
