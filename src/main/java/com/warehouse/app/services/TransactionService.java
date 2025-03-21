package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestAddStock;

public interface TransactionService {
    String addStockVariant(RequestAddStock req);
}
