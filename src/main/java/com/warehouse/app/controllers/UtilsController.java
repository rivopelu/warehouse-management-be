package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.annotations.PublicAccess;
import com.warehouse.app.dto.request.RequestSettingPrivileges;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.entities.RolePrivileges;
import com.warehouse.app.enums.PrivilegeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

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

    @PutMapping("setting/privilege")
    BaseResponse settingPrivileges(@RequestBody List<RequestSettingPrivileges> req);

    @GetMapping("v1/role-privilege")
    BaseResponse getRolePrivileges();

    @GetMapping("v1/list-privileges")
    BaseResponse getListPrivileges();
}
