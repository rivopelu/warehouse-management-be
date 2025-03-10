package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.dto.response.ResponseDetailWarehouse;
import com.warehouse.app.dto.response.ResponseListWarehouse;
import com.warehouse.app.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {

    String createWarehouse(RequestCreateWarehouse req);

    String editWarehouse(RequestCreateWarehouse req, String id);

    Page<ResponseListWarehouse> listWarehouse(Pageable pageable);

    ResponseDetailWarehouse detailWarehouse(String id);
}
