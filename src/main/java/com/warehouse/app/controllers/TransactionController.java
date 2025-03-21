package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.dto.request.RequestAddStock;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("transaction")
public interface TransactionController {

    @PostMapping("v1/add-stock")
    BaseResponse addStockVariant(@RequestBody RequestAddStock req);

}
