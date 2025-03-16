package com.warehouse.app.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warehouse.app.enums.UnitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RequestCreateVariant {
    private String name;
    private String description;
    private String productId;
    private String uniqueCode;
    private String imageUrl;
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
        private Integer value;
        private UnitTypeEnum parent;

    }
}
