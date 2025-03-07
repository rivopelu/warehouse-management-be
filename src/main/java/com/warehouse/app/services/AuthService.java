package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.ResponseSignIn;

public interface AuthService {

    String register(RequestCreateAccount requestCreateAccount);

    ResponseSignIn signIn(RequestSignIn requestSignIn);
}
