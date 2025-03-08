package com.warehouse.app.repositories;

import com.warehouse.app.entities.RolePrivileges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivileges, String> {
}
