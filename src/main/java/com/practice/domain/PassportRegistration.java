package com.practice.domain;

/*
 * @Created 7/28/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "PASSPORT_REGISTRATION")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String state;
    private String pincode;
    private String applicationType;
    private String bookletType;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private String temporaryId;

}
