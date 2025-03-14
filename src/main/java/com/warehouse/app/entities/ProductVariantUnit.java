package com.warehouse.app.entities;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variant_unit")
public class ProductVariantUnit extends BaseEntity {

    @JoinColumn(name = "parent_unit_id")
    @ManyToOne
    private UnitType parentUnit;

    @JoinColumn(name = "unit_id")
    @ManyToOne
    private UnitType unit;

    @JoinColumn(name = "variant_product_id")
    @ManyToOne
    private VariantProduct variantProduct;

    @Column(name = "quantity")
    private Integer quantity;

}
