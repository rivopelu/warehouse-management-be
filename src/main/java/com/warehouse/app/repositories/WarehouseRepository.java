package com.warehouse.app.repositories;

import com.warehouse.app.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
    Optional<Warehouse> findByIdAndActiveIsTrue(String id);

}
