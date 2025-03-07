package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.AuthController;
import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.AuthService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public BaseResponse register(RequestCreateAccount requestCreateAccount) {
        return ResponseHelper.createBaseResponse(authService.register(requestCreateAccount));
    }

    @Override
    public BaseResponse signInAdmin(RequestSignIn requestSignIn) {
        return ResponseHelper.createBaseResponse(authService.signIn(requestSignIn));
    }
}
