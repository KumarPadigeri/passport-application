package com.practice.repository;

/*
 * @Created 7/27/24
 * @Project passport-application
 * @User Kumar Padigeri
 */


import com.practice.domain.PassportRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRegistrationRepository extends JpaRepository<PassportRegistration, Long> {

    @Query("SELECT COUNT(e) FROM PASSPORT_REGISTRATION e")
    long countAllRecords();


}

