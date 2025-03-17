package com.warehouse.app.repositories;

import com.warehouse.app.entities.VariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VariantProductRepository extends JpaRepository<VariantProduct, String> {
    Optional<VariantProduct>  findBySlugOrUniqueCodeAndActiveIsTrue(String slug, String uniqueCode);

    List<VariantProduct> findByProductId(String id);
}
