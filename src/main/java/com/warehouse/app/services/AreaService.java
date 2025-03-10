package com.warehouse.app.services;

import com.warehouse.app.dto.response.ResponseAreaData;
import com.warehouse.app.dto.response.ResponseFullArea;

import java.math.BigInteger;
import java.util.List;

public interface AreaService {
    List<ResponseAreaData> getProvinceList();

    List<ResponseAreaData> getCityList(BigInteger provinceId);

    List<ResponseAreaData> getDistrictList(BigInteger cityId);

    List<ResponseAreaData> getSubDistrictList(BigInteger districtId);

    ResponseFullArea findAreaBySubDistrictId(BigInteger subDistrictId);

}
