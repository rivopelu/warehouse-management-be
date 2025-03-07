package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.annotations.PublicAccess;
import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("/auth")
public interface AuthController {


    @PublicAccess
    @PostMapping("v1/register")
    BaseResponse register(@RequestBody RequestCreateAccount requestCreateAccount);


    @PublicAccess
    @PostMapping("v1/admin/sign-in")
    BaseResponse signInAdmin(@RequestBody RequestSignIn requestSignIn);

}
