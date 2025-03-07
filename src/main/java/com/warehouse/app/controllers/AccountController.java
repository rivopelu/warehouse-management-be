package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController("account")
public interface AccountController {

    @GetMapping("v1/me")
    BaseResponse getMeAccount();
}
