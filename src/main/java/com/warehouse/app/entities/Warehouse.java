package com.warehouse.app.entities;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse")
public class Warehouse extends BaseEntity {

    private String name;

    @JoinColumn(name = "sub_district_id")
    @ManyToOne
    private SubDistrict subDistrictId;

    @Column(name = "address")
    private String address;

}
