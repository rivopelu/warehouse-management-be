package com.warehouse.app.services.impl;

import com.warehouse.app.constants.AuthConstant;
import com.warehouse.app.dto.response.ResponseAccountData;
import com.warehouse.app.entities.Account;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.AccountRepository;
import com.warehouse.app.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.warehouse.app.utilities.UtilsHelper.getMessage;

@Service
public class AccountServiceImpl implements AccountService {


    private final HttpServletRequest httpServletRequest;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(HttpServletRequest httpServletRequest, AccountRepository accountRepository) {
        this.httpServletRequest = httpServletRequest;
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseAccountData getMeAccount() {
        Account account = getCurrentAccount();
        if (account == null) {
            throw new BadRequestException(getMessage("account.not.found"));
        }
        try {
            return ResponseAccountData.builder()
                    .id(account.getId())
                    .email(account.getEmail())
                    .phoneNumber(account.getPhoneNumber())
                    .role(account.getRole())
                    .profilePicture(account.getProfilePicture())
                    .build();

        }catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Account getCurrentAccount() {
        String currentAccountId = getCurrentAccountId();
        Optional<Account> account = accountRepository.findById(currentAccountId);
        return account.orElse(null);
    }

    @Override
    public String getCurrentAccountId() {
        return httpServletRequest.getAttribute(AuthConstant.HEADER_X_WHO).toString();
    }
}
