package com.warehouse.app.entities;

import com.warehouse.app.enums.AccountRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.Collection;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private AccountRoleEnum name;


}
