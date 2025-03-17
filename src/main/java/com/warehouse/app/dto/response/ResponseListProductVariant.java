package com.warehouse.app.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warehouse.app.entities.UnitType;
import com.warehouse.app.enums.UnitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseListProductVariant {
    private String id;
    private String uniqueCode;
    private String imageUrl;
    private String name;
    private String description;
    private List<Units> units;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Units {
        private UnitTypeEnum type;
        private Integer quantity;
        private UnitTypeEnum parentType;
        private Boolean isMainParent;
        private Integer count;
    }
}
