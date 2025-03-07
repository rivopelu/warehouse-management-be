package com.warehouse.app.repositories;

import com.warehouse.app.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface CityRepository extends JpaRepository<City, BigInteger> {
    List<City> findByProvinceId(BigInteger provinceId);
}
