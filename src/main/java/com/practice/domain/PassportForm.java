package com.practice.domain;

/*
 * @Created 7/28/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportForm {
    private String userId;
    private String country;
    private String state;
    private String city;
    private String pin;
    private String applicationType;
    private String bookletType;
    private String issueDate;

}
