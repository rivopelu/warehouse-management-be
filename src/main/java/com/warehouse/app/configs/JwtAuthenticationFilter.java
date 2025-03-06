package com.warehouse.app.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.app.constants.AuthConstant;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${auth.secret}")
    private String SECRET;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(SECRET)
                            .build()
                            .parseClaimsJws(jwt)
                            .getBody();
                    String userId = claims.get(AuthConstant.HEADER_X_WHO, String.class);
                    request.setAttribute(AuthConstant.HEADER_X_WHO, userId);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                } else {
                    createHttpUnAuthentication(response, "Invalid Token");
                }
            } else if (userEmail == null) {
                createHttpUnAuthentication(response, "Invalid Token");
            } else {
                filterChain.doFilter(request, response);
            }

        } catch (Exception ex) {
            createHttpUnAuthentication(response, ex.getMessage());
        }
    }

    private void createHttpUnAuthentication(HttpServletResponse httpServletResponse, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BaseResponse response = BaseResponse.builder()
                .success(false)
                .message(message)
                .build();
        String json = mapper.writeValueAsString(response);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}


