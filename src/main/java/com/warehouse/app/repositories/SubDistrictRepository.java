package com.warehouse.app.repositories;

import com.warehouse.app.entities.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SubDistrictRepository extends JpaRepository<SubDistrict, BigInteger> {
}
