package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.entities.Warehouse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("warehouse")
public interface WarehouseController {

    @PostMapping("v1/create")
    BaseResponse createWarehouse(@RequestBody RequestCreateWarehouse req);

}
