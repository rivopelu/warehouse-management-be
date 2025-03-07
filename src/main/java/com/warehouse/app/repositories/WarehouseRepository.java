package com.warehouse.app.repositories;

import com.warehouse.app.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
}
