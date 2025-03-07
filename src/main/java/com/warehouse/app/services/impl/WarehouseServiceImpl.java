package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.entities.SubDistrict;
import com.warehouse.app.entities.Warehouse;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.SubDistrictRepository;
import com.warehouse.app.repositories.WarehouseRepository;
import com.warehouse.app.services.AccountService;
import com.warehouse.app.services.WarehouseService;
import com.warehouse.app.utilities.EntityUtils;
import com.warehouse.app.utilities.UtilsHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.warehouse.app.utilities.UtilsHelper.getMessage;

@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final SubDistrictRepository subDistrictRepository;
    private final AccountService accountService;
    private final WarehouseRepository warehouseRepository;

    @Override
    public String createWarehouse(RequestCreateWarehouse req) {
        SubDistrict subDistrict = subDistrictRepository.findById(req.getSubDistrictId()).orElseThrow(() -> new NotFoundException(getMessage("sub.district.not.found")));
        try {
            Warehouse warehouse = Warehouse.builder()
                    .address(req.getAddress())
                    .name(req.getName())
                    .subDistrict(subDistrict)
                    .build();
            EntityUtils.created(warehouse, accountService.getCurrentAccountId());
            warehouseRepository.save(warehouse);
            return getMessage("warehouse.created");
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
