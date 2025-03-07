package com.warehouse.app.repositories;

import com.warehouse.app.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface DistrictRepository extends JpaRepository<District, BigInteger> {
    List<District> findByCityId(BigInteger cityId);
}
