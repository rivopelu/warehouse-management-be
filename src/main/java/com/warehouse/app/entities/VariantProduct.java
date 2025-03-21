package com.warehouse.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variant_product")
public class VariantProduct extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "slug")
    private String slug;

    @Column(name = "image_url")
    private String imageUrl;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @Column(name = "unique_code")
    private String uniqueCode;

    @OneToMany(mappedBy = "variantProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariantUnit> variantUnits;

}
