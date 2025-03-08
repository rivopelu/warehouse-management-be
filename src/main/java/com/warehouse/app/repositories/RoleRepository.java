package com.warehouse.app.repositories;

import com.warehouse.app.entities.Role;
import com.warehouse.app.enums.AccountRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(AccountRoleEnum name);
}
