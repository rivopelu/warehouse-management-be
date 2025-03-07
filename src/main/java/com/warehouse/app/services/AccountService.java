package com.warehouse.app.services;

import com.warehouse.app.dto.response.ResponseAccountData;
import com.warehouse.app.entities.Account;

public interface AccountService {

    ResponseAccountData getMeAccount();

    Account getCurrentAccount();

    String getCurrentAccountId();
}
