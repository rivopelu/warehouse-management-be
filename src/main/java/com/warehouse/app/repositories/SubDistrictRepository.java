package com.warehouse.app.repositories;

import com.warehouse.app.entities.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface SubDistrictRepository extends JpaRepository<SubDistrict, BigInteger> {
    List<SubDistrict> findByDistrictId(BigInteger districtId);
}
