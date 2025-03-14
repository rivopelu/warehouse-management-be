package com.warehouse.app.utilities;

import com.warehouse.app.entities.Privilege;
import com.warehouse.app.entities.Role;
import com.warehouse.app.entities.RolePrivileges;
import com.warehouse.app.entities.UnitType;
import com.warehouse.app.enums.AccountRoleEnum;
import com.warehouse.app.enums.PrivilegeEnum;
import com.warehouse.app.enums.UnitTypeEnum;
import com.warehouse.app.repositories.PrivilegeRepository;
import com.warehouse.app.repositories.RolePrivilegeRepository;
import com.warehouse.app.repositories.RoleRepository;
import com.warehouse.app.repositories.UnitTypeRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final UnitTypeRepository unitTypeRepository;

    boolean alreadySetup = false;


    private final PrivilegeRepository privilegeRepository;


    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        PrivilegeEnum[] privileges = PrivilegeEnum.values();
        UnitTypeEnum[] unitTypes = UnitTypeEnum.values();
        if (alreadySetup) return;
        for (PrivilegeEnum privilege : privileges) {
            createPrivilegeIfNotFound(privilege);
        }
        for (UnitTypeEnum unitType : unitTypes) {
            createUnitTypeIfNotFound(unitType);
        }
        alreadySetup = true;
    }

    @Transactional
    void createUnitTypeIfNotFound(UnitTypeEnum unitType) {
        Optional<UnitType> findUnitType = unitTypeRepository.findByName(unitType);
        if (findUnitType.isEmpty()){
            UnitType newUnitType = new UnitType();
            newUnitType.setName(unitType);
            unitTypeRepository.save(newUnitType);
        }
    }

    @Transactional
    void createPrivilegeIfNotFound(PrivilegeEnum name) {
        Optional<Privilege> findPrivilege = privilegeRepository.findByName(name);
        if (findPrivilege.isEmpty()) {
            Privilege privilege = Privilege.builder().name(name).build();
            Privilege PrivilegeSaved = privilegeRepository.save(privilege);
            Role findRole = roleRepository.findByName(AccountRoleEnum.SUPER_ADMIN).orElse(null);
            if (findRole == null) {
                throw new IllegalStateException("Role not found");
            }
            RolePrivileges rolePrivileges = RolePrivileges.builder().role(findRole).privilege(PrivilegeSaved).build();
            rolePrivilegeRepository.save(rolePrivileges);
        }
    }

}