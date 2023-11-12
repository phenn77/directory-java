package com.training.directory.repository;

import com.training.directory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE username = :username OR email = :email", nativeQuery = true)
    User findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);
}
