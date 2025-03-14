package com.warehouse.app.repositories;

import com.warehouse.app.entities.UnitType;
import com.warehouse.app.enums.UnitTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitTypeRepository extends JpaRepository<UnitType, String> {
    Optional<UnitType> findByName(UnitTypeEnum unitType);
}
