package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.WarehouseController;
import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.WarehouseService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@BaseControllerImpl
@RequiredArgsConstructor
public class WarehouseControllerImpl implements WarehouseController {

    private final WarehouseService warehouseService;

    @Override
    public BaseResponse createWarehouse(RequestCreateWarehouse req) {
        return ResponseHelper.createBaseResponse(warehouseService.createWarehouse(req));
    }

    @Override
    public BaseResponse editWarehouse(RequestCreateWarehouse req, String id) {
        return ResponseHelper.createBaseResponse(warehouseService.editWarehouse(req, id));
    }

    @Override
    public BaseResponse listWarehouse(Pageable pageable) {
       return ResponseHelper.createBaseResponse(warehouseService.listWarehouse(pageable));
    }

    @Override
    public BaseResponse detailWarehouse(String id) {
        return ResponseHelper.createBaseResponse(warehouseService.detailWarehouse(id));
    }
}
