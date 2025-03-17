package com.warehouse.app.repositories;

import com.warehouse.app.entities.ProductVariantUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantUnitRepository extends JpaRepository<ProductVariantUnit, String> {
}
