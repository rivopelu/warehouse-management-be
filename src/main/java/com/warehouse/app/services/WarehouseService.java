package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.entities.Warehouse;

public interface WarehouseService {

    String createWarehouse(RequestCreateWarehouse req);
}
