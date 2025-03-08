package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSettingPrivileges;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.ResponseSignIn;

import java.util.List;

public interface AuthService {

    String register(RequestCreateAccount requestCreateAccount);

    ResponseSignIn signIn(RequestSignIn requestSignIn);

    String settingPrivileges(List<RequestSettingPrivileges> req);
}
