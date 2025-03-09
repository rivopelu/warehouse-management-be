package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSettingPrivileges;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.ResponseRolePrivileges;
import com.warehouse.app.dto.response.ResponseSignIn;
import com.warehouse.app.enums.PrivilegeEnum;

import java.util.List;

public interface AuthService {

    String register(RequestCreateAccount requestCreateAccount);

    ResponseSignIn signIn(RequestSignIn requestSignIn);

    String settingPrivileges(List<RequestSettingPrivileges> req);

    List<PrivilegeEnum> getListPrivileges();

    List<ResponseRolePrivileges> getRolePrivileges();
}
