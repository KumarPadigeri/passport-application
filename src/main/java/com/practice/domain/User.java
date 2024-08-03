package com.practice.domain;

/*
 * @Created 7/27/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String confirmPassword;
    private String role;
    private String email;
}
