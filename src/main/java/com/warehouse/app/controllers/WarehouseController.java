package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@BaseController("warehouse")
public interface WarehouseController {

    @PostMapping("v1/create")
    BaseResponse createWarehouse(@RequestBody RequestCreateWarehouse req);

    @PutMapping("v1/edit/{id}")
    BaseResponse editWarehouse(@RequestBody RequestCreateWarehouse req, @PathVariable("id") String id);


    @GetMapping("v1/list")
    BaseResponse listWarehouse(Pageable pageable);

}
