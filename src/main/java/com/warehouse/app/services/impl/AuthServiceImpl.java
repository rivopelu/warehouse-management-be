package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateAccount;
import com.warehouse.app.dto.request.RequestSettingPrivileges;
import com.warehouse.app.dto.request.RequestSignIn;
import com.warehouse.app.dto.response.ResponseAccountData;
import com.warehouse.app.dto.response.ResponseSignIn;
import com.warehouse.app.entities.Account;
import com.warehouse.app.entities.Privilege;
import com.warehouse.app.entities.Role;
import com.warehouse.app.entities.RolePrivileges;
import com.warehouse.app.enums.AccountRoleEnum;
import com.warehouse.app.enums.PrivilegeEnum;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.AccountRepository;
import com.warehouse.app.repositories.PrivilegeRepository;
import com.warehouse.app.repositories.RolePrivilegeRepository;
import com.warehouse.app.repositories.RoleRepository;
import com.warehouse.app.services.AuthService;
import com.warehouse.app.services.JwtService;
import com.warehouse.app.utilities.EntityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.warehouse.app.utilities.UtilsHelper.generateAvatar;
import static com.warehouse.app.utilities.UtilsHelper.getMessage;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public String register(RequestCreateAccount requestCreateAccount) {

        boolean checkEmail = accountRepository.existsByEmailAndActiveIsTrue(requestCreateAccount.getEmail());
        if (checkEmail) {
            throw new BadRequestException(getMessage("existed.email"));
        }

        Role role = roleRepository.findByName(AccountRoleEnum.ADMIN).orElseThrow(() -> new NotFoundException(getMessage("role.not.found")));

        String profilePicture = generateAvatar(requestCreateAccount.getName());
        String encodedPassword = passwordEncoder.encode(requestCreateAccount.getPassword());
        Account account = Account.builder()
                .name(requestCreateAccount.getName())
                .email(requestCreateAccount.getEmail())
                .password(encodedPassword)
                .profilePicture(profilePicture)
                .role(role)
                .phoneNumber(requestCreateAccount.getPhoneNumber())
                .build();
        try {
            String id = UUID.randomUUID().toString();
            account.setId(id);
            EntityUtils.created(account, id);
            accountRepository.save(account);
            return getMessage("success");
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseSignIn signIn(RequestSignIn requestSignIn) {
        Account account = accountRepository.findByEmailAndActiveIsTrue(requestSignIn.getEmail()).orElseThrow(() -> new BadRequestException(getMessage("failed.login")));
        return buildSignIn(account, requestSignIn.getPassword());
    }

    @Override
    @Transactional
    public String settingPrivileges(List<RequestSettingPrivileges> req) {
        List<AccountRoleEnum> accountRoleEnums = req.stream().map(RequestSettingPrivileges::getRole).toList();
        List<Role> roleList = roleRepository.findAllByName(accountRoleEnums);
        if (roleList.size() != req.size()) {
            throw new BadRequestException(getMessage("role.not.found"));
        }
        try {
            List<RolePrivileges> rolePrivilegesList = new ArrayList<>();
            for (RequestSettingPrivileges role : req) {
                if (role.getRole() != AccountRoleEnum.SUPER_ADMIN) {
                    Role findRole = roleList.stream().filter(r -> r.getName() == role.getRole()).findFirst().orElseThrow(() -> new BadRequestException(getMessage("role.not.found")));
                    List<Privilege> findPrivilege = privilegeRepository.findAllByName(role.getPrivileges());
                    for (Privilege privilege : findPrivilege) {
                        RolePrivileges rolePrivileges = RolePrivileges.builder()
                                .privilege(privilege)
                                .role(findRole)
                                .build();
                        rolePrivilegesList.add(rolePrivileges);
                    }
                }
            }
            Role findRoleSuperAdmin = roleRepository.findByName(AccountRoleEnum.SUPER_ADMIN).orElseThrow(() -> new NotFoundException(getMessage("role.not.found")));
            List<Privilege> privilegeList = privilegeRepository.findAll();
            for (Privilege privilege : privilegeList) {
                RolePrivileges rolePrivileges = RolePrivileges.builder()
                        .privilege(privilege)
                        .role(findRoleSuperAdmin)
                        .build();
                rolePrivilegesList.add(rolePrivileges);
            }
            rolePrivilegeRepository.deleteAll();
            rolePrivilegeRepository.saveAll(rolePrivilegesList);
            return "SUCCESS";
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private ResponseSignIn buildSignIn(Account account, String password) {
        Authentication authentication;
        List<PrivilegeEnum> privilegeList = new ArrayList<>();
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getEmail(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);
            ResponseAccountData accountData = buildAccountData(account);
            for (Privilege privilege : account.getRole().getPrivileges()) {
                privilegeList.add(privilege.getName());
            }
            return ResponseSignIn.builder().accessToken(jwt)
                    .accountData(accountData)
                    .privileges(privilegeList)
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
