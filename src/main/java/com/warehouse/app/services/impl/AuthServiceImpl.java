package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.ResponseAccountData;
import com.warehouse.app.dto.response.ResponseSignIn;
import com.warehouse.app.entities.Account;
import com.warehouse.app.enums.AccountRoleEnum;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.AccountRepository;
import com.warehouse.app.services.AuthService;
import com.warehouse.app.services.JwtService;
import com.warehouse.app.utilities.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
                .build();
       try {
           String id = UUID.randomUUID().toString();
           account.setId(id);
           EntityUtils.created(account, id);
           accountRepository.save(account);
           return getMessage("success");
       }catch (Exception e){
           throw new SystemErrorException(e);
       }
    }

    @Override
    public ResponseSignIn signIn(RequestSignIn requestSignIn) {
       Account account = accountRepository.findByEmailAndActiveIsTrue(requestSignIn.getEmail()).orElseThrow(() -> new BadRequestException(getMessage("failed.login")));
       if (!account.getRole().equals(AccountRoleEnum.ADMIN)) {
           throw new BadRequestException(getMessage("failed.login"));
       }
        return buildSignIn(account, requestSignIn.getPassword());
    }

    private ResponseSignIn buildSignIn(Account account, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getEmail(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);
            ResponseAccountData accountData = buildAccountData(account);
            return ResponseSignIn.builder().accessToken(jwt)
                    .accountData(accountData)
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadRequestException(getMessage("failed.login"));
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private ResponseAccountData buildAccountData(Account account) {
        return ResponseAccountData.builder()
                .id(account.getId())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .name(account.getName())
                .role(account.getRole().getName())
                .profilePicture(account.getProfilePicture())
                .build();
    }
}
