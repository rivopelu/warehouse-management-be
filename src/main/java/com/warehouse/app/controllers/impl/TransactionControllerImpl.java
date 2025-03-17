package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.TransactionController;
import com.warehouse.app.dto.request.RequestAddStock;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.TransactionService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;
    @Override
    public BaseResponse addStockVariant(RequestAddStock req) {
        return ResponseHelper.createBaseResponse(transactionService.addStockVariant(req));
    }
}
