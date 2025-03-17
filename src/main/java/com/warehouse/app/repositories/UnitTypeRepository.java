package com.warehouse.app.repositories;

import com.warehouse.app.entities.UnitType;
import com.warehouse.app.enums.UnitTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnitTypeRepository extends JpaRepository<UnitType, String> {
    Optional<UnitType> findByName(UnitTypeEnum unitType);

    @Query("select ut from UnitType  as ut where  ut.name in :uniTypeEnumList")
    List<UnitType> findAllInNames(List<UnitTypeEnum> unitTypeEnumList);
}
