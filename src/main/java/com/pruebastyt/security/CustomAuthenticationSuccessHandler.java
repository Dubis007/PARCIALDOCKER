package com.pruebastyt.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = "/";

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/admin/dashboard";
                break;
            } else if (role.equals("ROLE_COORDINADOR")) {
                redirectUrl = "/coordinador/dashboard";
                break;
            } else if (role.equals("ROLE_DOCENTE")) {
                redirectUrl = "/docente/dashboard";
                break;
            } else if (role.equals("ROLE_ESTUDIANTE")) {
                redirectUrl = "/estudiante/dashboard";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}