package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestAddStock;
import com.warehouse.app.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Override
    public String addStockVariant(RequestAddStock req) {
        return "SUCCESS";
    }
}
