package com.warehouse.app.utilities;

import com.warehouse.app.entities.BaseEntity;

import java.util.Date;
import java.util.UUID;

public class EntityUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static <T extends BaseEntity> void created(T entity, String userId) {
        if (entity.getId() == null || entity.getId().isBlank())
            entity.setId(getUUID());
        if (entity.getCreatedBy() == null || entity.getCreatedBy().isBlank())
            entity.setCreatedBy(userId);
        if (entity.getCreatedDate() == null)
            entity.setCreatedDate(new Date().getTime());
        if (entity.getUpdatedDate() == null)
            entity.setCreatedDate(new Date().getTime());
        if (entity.getUpdatedDate() == null || entity.getUpdatedBy().isBlank())
            entity.setUpdatedBy(userId);
    }


    public static <T extends BaseEntity> void updated(T entity, String userId) {
        entity.setUpdatedBy(userId);
        entity.setUpdatedDate(new Date().getTime());
    }
}




