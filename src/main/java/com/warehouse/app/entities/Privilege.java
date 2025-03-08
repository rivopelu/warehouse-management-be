package com.warehouse.app.entities;

import com.warehouse.app.enums.AccountRoleEnum;
import com.warehouse.app.enums.PrivilegeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privilege")
public class Privilege {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private PrivilegeEnum name;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }


}
