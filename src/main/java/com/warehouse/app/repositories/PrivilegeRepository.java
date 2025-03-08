package com.warehouse.app.repositories;

import com.warehouse.app.entities.Privilege;
import com.warehouse.app.enums.PrivilegeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface  PrivilegeRepository extends JpaRepository<Privilege, String> {
    Optional<Privilege> findByName(PrivilegeEnum name);

    @Query("select p from Privilege as p where p.name in :name")
    List<Privilege> findAllByName(List<PrivilegeEnum> name);
}
