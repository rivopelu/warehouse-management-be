package com.warehouse.app.repositories;

import com.warehouse.app.entities.Role;
import com.warehouse.app.enums.AccountRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(AccountRoleEnum name);

    @Query("SELECT r FROM Role r WHERE r.name IN :names")
    List<Role> findAllByName(@Param("names") List<AccountRoleEnum> names);
}
