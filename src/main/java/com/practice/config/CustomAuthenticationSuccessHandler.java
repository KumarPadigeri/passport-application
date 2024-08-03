package com.practice.config;

/*
 * @Created 7/29/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    public CustomAuthenticationSuccessHandler(String defaultTargetUrl) {
        log.error("CustomAuthenticationSuccessHandler with  constructor "+ defaultTargetUrl);
        setDefaultTargetUrl(defaultTargetUrl);
    }

    public CustomAuthenticationSuccessHandler() {
        log.error("CustomAuthenticationSuccessHandler zero constructor");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        log.info("Redirecting to /showForm after successful login");
        response.sendRedirect("/showForm");
        //  redirectStrategy.sendRedirect(request, response, "/showForm");
    }


}
