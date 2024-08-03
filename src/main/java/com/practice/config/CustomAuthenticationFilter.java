package com.practice.config;

/*
 * @Created 7/27/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.error("COkkies ....   " + Arrays.toString(request.getCookies()));
        log.error("SESSION id :::  --> " + Arrays.toString(Arrays.toString(new boolean[]{StringUtils.isNotBlank(request.getRequestedSessionId())}).toCharArray()));
        log.error("SESSION id :::  --> " + request.getRequestURI().contains("/passport-application"));

//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new BadCredentialsException("Username or password is incorrect");
//        }

        if (username == null || password == null) {
            throw new BadCredentialsException("Username or password should not be empty");
        }


        // UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authRequest);
    }


}

