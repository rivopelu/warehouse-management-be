package com.warehouse.app.repositories;

import com.warehouse.app.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, String> {

    @Query("select s from Stock  as s where s.variantProduct.id = :variantId  and s.warehouse.id =:warehouseId")
    List<Stock> findByVariantProductIdAndWarehouseId(String variantId, String warehouseId);

    List<Stock> findByVariantProductId(String variantId);
}
