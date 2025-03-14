package com.warehouse.app.entities;


import com.warehouse.app.enums.UnitTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit_type")
public class UnitType  {

    @Id
    private String id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private UnitTypeEnum name;


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
