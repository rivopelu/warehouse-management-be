package com.warehouse.app.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock extends BaseEntity {
    @JoinColumn(name = "warehouse_id")
    @ManyToOne
    private Warehouse warehouse;

    @JoinColumn(name = "unit_type_id")
    @ManyToOne
    private UnitType unitType;

    @JoinColumn(name = "variant_product_id")
    @ManyToOne
    private VariantProduct variantProduct;

    @Column(name = "quantity")
    private Integer quantity;
}
