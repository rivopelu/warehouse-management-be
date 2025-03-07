package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.annotations.PublicAccess;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;

@BaseController
public interface UtilsController {

    @PublicAccess
    @GetMapping("/ping")
    BaseResponse ping();

    @GetMapping("area/v1/province")
    BaseResponse getProvinceList();

    @GetMapping("area/v1/city/{provinceId}")
    BaseResponse getProvinceList(@PathVariable BigInteger provinceId);

    @GetMapping("area/v1/district/{cityId}")
    BaseResponse getListDistrict(@PathVariable BigInteger cityId);

    @GetMapping("area/v1/sub-district/{districtId}")
    BaseResponse getListSubDistrict(@PathVariable BigInteger districtId);
}
