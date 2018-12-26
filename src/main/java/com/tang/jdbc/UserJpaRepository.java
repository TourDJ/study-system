package com.tang.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tang.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByUsernameOrUserid(String username, String userid);
    
}
