package com.warehouse.app.services.impl;

import com.warehouse.app.entities.Account;
import com.warehouse.app.entities.Privilege;
import com.warehouse.app.enums.PrivilegeEnum;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.repositories.AccountRepository;
import com.warehouse.app.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.warehouse.app.constants.AuthConstant.*;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${auth.secret}")
    private String SECRET;

    @Value("${auth.expiration_time}")
    private long EXPIRATION_TIME;

    private final AccountRepository accountRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails) {

        Optional<Account> findAccount = accountRepository.findByEmailAndActiveIsTrue(userDetails.getUsername());
        if (findAccount.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }
        Account account = findAccount.get();
        List<PrivilegeEnum> privilegeEnumList = account.getRole()
                .getPrivileges()
                .stream()
                .map(Privilege::getName)
                .toList();


        Map<String, Object> claims = new HashMap<>();
        claims.put(HEADER_X_WHO, account.getId());
        claims.put(HEADER_X_ROLE, account.getRole().getName());
        claims.put(HEADER_X_MAIL, account.getEmail());
        claims.put(HEADER_X_PRIVILEGES, privilegeEnumList);
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
}
