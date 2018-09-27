package com.tang.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByUsernameOrUserid(String username, String userid);
    
}
