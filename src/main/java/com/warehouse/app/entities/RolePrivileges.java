package com.warehouse.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_privileges")
public class RolePrivileges {

    @Id
    private String id;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    @JoinColumn(name = "privilege_id")
    @ManyToOne
    private Privilege privilege;


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }


}
