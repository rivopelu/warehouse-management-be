package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String register(RequestCreateAccount requestCreateAccount) {
        return "success";
    }
}
