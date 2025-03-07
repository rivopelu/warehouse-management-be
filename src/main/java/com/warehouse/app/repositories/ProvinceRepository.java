package com.warehouse.app.repositories;

import com.warehouse.app.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ProvinceRepository extends JpaRepository<Province, BigInteger> {
}
