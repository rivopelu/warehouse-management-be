package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.UtilsController;
import com.warehouse.app.dto.request.RequestSettingPrivileges;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.AreaService;
import com.warehouse.app.services.AuthService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@BaseControllerImpl
@RequiredArgsConstructor
public class UtilsControllerImpl implements UtilsController {

    private final AreaService areaService;
    private final AuthService authService;

    @Override
    public BaseResponse ping() {
        return ResponseHelper.createBaseResponse("Pong");
    }

    @Override
    public BaseResponse getProvinceList() {
        return ResponseHelper.createBaseResponse(areaService.getProvinceList());
    }

    @Override
    public BaseResponse getProvinceList(BigInteger provinceId) {
        return ResponseHelper.createBaseResponse(areaService.getCityList(provinceId));
    }

    @Override
    public BaseResponse getListDistrict(BigInteger cityId) {
        return ResponseHelper.createBaseResponse(areaService.getDistrictList(cityId));
    }

    @Override
    public BaseResponse getListSubDistrict(BigInteger districtId) {
        return ResponseHelper.createBaseResponse(areaService.getSubDistrictList(districtId));
    }

    @Override
    public BaseResponse settingPrivileges(List<RequestSettingPrivileges> req) {

        return ResponseHelper.createBaseResponse(authService.settingPrivileges(req));
    }
}
