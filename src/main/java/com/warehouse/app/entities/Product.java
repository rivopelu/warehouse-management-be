package com.warehouse.app.entities;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "slug")
    private String slug;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;
    
}
