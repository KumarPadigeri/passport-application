package com.practice.config;

/*
 * @Created 7/27/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import com.practice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (Objects.nonNull(user) && user.getUsername().equals(username)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword()) // Password should be encoded in production
                    .roles(user.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
