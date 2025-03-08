package com.warehouse.app.utilities;

import com.warehouse.app.entities.Privilege;
import com.warehouse.app.enums.PrivilegeEnum;
import com.warehouse.app.repositories.PrivilegeRepository;
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

    boolean alreadySetup = false;



    private final PrivilegeRepository privilegeRepository;


    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        PrivilegeEnum[] privileges = PrivilegeEnum.values();
        if (alreadySetup)
            return;
        for (PrivilegeEnum privilege : privileges) {
            createPrivilegeIfNotFound(privilege);
        }
        alreadySetup = true;
    }


    @Transactional
    void createPrivilegeIfNotFound(PrivilegeEnum name) {
        Optional<Privilege> findPrivilege = privilegeRepository.findByName(name);
        if (findPrivilege.isEmpty()) {
            Privilege privilege = Privilege.builder()
                    .name(name)
                    .build();
            privilegeRepository.save(privilege);
        }
    }

}