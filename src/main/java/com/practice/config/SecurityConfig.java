package com.practice.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

/*
 * @Created 1/10/2023 12:56 PM 2023
 * @Project testing-project-springboot3
 * @User Kumar Padigeri
 */

@Configuration
@EnableJpaAuditing
@Slf4j
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/",
            "/error",
            "/api-docs/**",
            "/swagger-ui/index.html",
            "/swagger-ui/index.html/**",
            "/documentation",
            "/documentation/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "webjars/**",
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // CSA Controllers
            "/csa/api/token",
            // Actuators
            "/actuator/**",
            "/health/**",
            "/",
            "/home",
            "/public/**",
            "/login",
            "/error",
            "/perform_login",
            "/static/**",
            "/styles.css"
    };

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler successHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(HttpSecurity http) throws Exception {
        return new CustomAuthenticationFilter("/perform_login", authenticationManager(http), customUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(encoder())
//                .and()
//                .build();
//    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

  /*  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     //   CustomAuthenticationFilter customFilter = customAuthenticationFilter(http);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                       // .successForwardUrl("/showForm")
                    //    .successForwardUrl("/success.html")
                       // .loginProcessingUrl("/perform_login")
                      //  .successHandler((request, response, authentication) -> response.sendRedirect("/success"))
                        .permitAll()
               )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/test/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/pet/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/management/**").hasRole("ADMIN")
                                .requestMatchers("/showForm").authenticated()
//                        .requestMatchers("/submitPassportApplication").permitAll()
//
//                        .requestMatchers("/register").permitAll()
//                        .requestMatchers("/updateCities").permitAll()
//                        .requestMatchers("/cities/state?/**").permitAll()

                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
      //          .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
        ;
        return http.build();
    }
   */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customFilter = customAuthenticationFilter(http);
//        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        http
                .anonymous(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/test/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/pet/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/management/**").hasRole("ADMIN")
                        .requestMatchers("/showForm").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/updateCities").permitAll()
                        .requestMatchers("/submitPassportApplication").permitAll()
                        .requestMatchers("/register").permitAll()
                        //   .requestMatchers("/cities/state?/**").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
//                        .defaultSuccessUrl("/passportForm.html", true)
//                        .successForwardUrl("/showForm")
//                        .successHandler((request, response, authentication) -> {
//                            log.error("AT SUCcESS HANdler");
//                            redirectStrategy.sendRedirect(request, response, "/showForm");
//                        })
                        // .defaultSuccessUrl("/passport-application/showForm")
                        //.loginProcessingUrl("passport-application/showForm")
                        //.successForwardUrl("/showForm")
                        .permitAll()
                )
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

//    @Bean
//    AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler("/showForm");
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }
}
