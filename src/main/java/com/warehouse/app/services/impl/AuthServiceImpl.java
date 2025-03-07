package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.entities.Account;
import com.warehouse.app.enums.AccountRoleEnum;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.repositories.AccountRepository;
import com.warehouse.app.services.AuthService;
import com.warehouse.app.utilities.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.warehouse.app.utilities.UtilsHelper.generateAvatar;
import static com.warehouse.app.utilities.UtilsHelper.getMessage;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Override
    public String register(RequestCreateAccount requestCreateAccount) {

        boolean checkEmail = accountRepository.existsByEmailAndActiveIsTrue(requestCreateAccount.getEmail());
        if (checkEmail) {
            throw new BadRequestException(getMessage("existed.email"));
        }

        String profilePicture = generateAvatar(requestCreateAccount.getName());
        String encodedPassword = passwordEncoder.encode(requestCreateAccount.getPassword());
        Account account = Account.builder()
                .name(requestCreateAccount.getName())
                .email(requestCreateAccount.getEmail())
                .password(encodedPassword)
                .profilePicture(profilePicture)
                .phoneNumber(requestCreateAccount.getPhoneNumber())
                .role(AccountRoleEnum.ADMIN)
                .build();
        String id = UUID.randomUUID().toString();
        account.setId(id);
        EntityUtils.created(account, id);
        accountRepository.save(account);

        return getMessage("success");
    }
}
