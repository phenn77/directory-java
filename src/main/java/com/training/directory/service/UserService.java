package com.training.directory.service;

import com.training.directory.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    User getProfile();
}
