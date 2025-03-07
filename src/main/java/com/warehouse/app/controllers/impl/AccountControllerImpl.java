package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.AccountController;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.AccountService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;
    @Override
    public BaseResponse getMeAccount() {
        return ResponseHelper.createBaseResponse(accountService.getMeAccount());
    }
}
