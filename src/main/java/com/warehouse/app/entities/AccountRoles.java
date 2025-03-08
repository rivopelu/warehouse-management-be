package com.warehouse.app.entities;

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
@Table(name = "account_roles")
public class AccountRoles {
    @Id
    private String id;


    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;



    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
