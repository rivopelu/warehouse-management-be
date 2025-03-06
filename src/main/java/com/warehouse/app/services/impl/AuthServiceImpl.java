package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.warehouse.app.utilities.UtilsHelper.getMessage;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public String register(RequestCreateAccount requestCreateAccount) {
        return getMessage("success");
    }
}
