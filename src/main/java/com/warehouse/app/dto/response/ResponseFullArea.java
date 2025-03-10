package com.warehouse.app.dto.response;

import com.warehouse.app.entities.Province;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseFullArea {
    private ResponseAreaData province;
    private ResponseAreaData city;
    private ResponseAreaData district;
    private ResponseAreaData subDistrict;
}
