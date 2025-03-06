package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateAccount;

public interface AuthService {

    String register(RequestCreateAccount requestCreateAccount);
}
