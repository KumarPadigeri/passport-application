package com.practice.repository;

/*
 * @Created 7/27/24
 * @Project passport-application
 * @User Kumar Padigeri
 */


import com.practice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);


}

