package com.training.directory.repository;

import com.training.directory.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username OR email = :email")
    User findByUsernameOrEmail(String username, String email);
}
