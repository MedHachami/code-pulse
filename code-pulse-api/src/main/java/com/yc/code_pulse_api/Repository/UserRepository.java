package com.yc.code_pulse_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yc.code_pulse_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    // User findByEmail(String email);
    // User findById(Long id);
    // User findByUsernameAndEmail(String username, String email);
    
}
