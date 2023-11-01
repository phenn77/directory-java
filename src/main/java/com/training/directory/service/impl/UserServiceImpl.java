package com.training.directory.service.impl;

import com.training.directory.dao.ApiContext;
import com.training.directory.model.User;
import com.training.directory.repository.UserRepository;
import com.training.directory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final ApiContext apiContext;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found. Username: {}", username);

                    return new UsernameNotFoundException("User not found");
                });
    }

    @Override
    public User getProfile() {
        return apiContext.getUser();
    }
}
