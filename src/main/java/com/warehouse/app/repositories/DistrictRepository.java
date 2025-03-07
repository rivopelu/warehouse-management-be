package com.warehouse.app.repositories;

import com.warehouse.app.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface DistrictRepository extends JpaRepository<District, BigInteger> {
}
