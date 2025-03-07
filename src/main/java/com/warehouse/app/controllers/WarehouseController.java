package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("warehouse")
public interface WarehouseController {

    @PostMapping("v1/create")
    BaseResponse createWarehouse(@RequestBody RequestCreateWarehouse req);

    @PutMapping("v1/edit/{id}")
    BaseResponse editWarehouse(@RequestBody RequestCreateWarehouse req, @PathVariable("id") String id);

}
