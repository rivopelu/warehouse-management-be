package com.warehouse.app.repositories;

import com.warehouse.app.entities.Privilege;
import com.warehouse.app.enums.PrivilegeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  PrivilegeRepository extends JpaRepository<Privilege, String> {
    Optional<Privilege> findByName(PrivilegeEnum name);
}
