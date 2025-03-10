package com.warehouse.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_district")
public class SubDistrict {
    @Id
    private BigInteger id;

    @JoinColumn(name = "district_id")
    @ManyToOne
    private District district;

    @JoinColumn(name = "city_id")
    @ManyToOne
    private City city;

    @Column(name = "province_id")
    private BigInteger provinceId;

    @Column(name = "name")
    private String name;

    @Column(name = "postcode")
    private BigInteger postCode;


}


