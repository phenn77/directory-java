package com.training.directory.service.impl;

import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.repository.UserRepository;
import com.training.directory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApiContext apiContext;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found. Username: {}", username);

                    return new UsernameNotFoundException("User not found.");
                });
    }

    @Override
    public ResponseBody getProfile() {
        var user = Objects.nonNull(apiContext.getUser()) ? apiContext.getUser() : null;
        var status = Objects.nonNull(user) ? Status.SUCCESS : Status.ERROR;

        return new ResponseBody(status, StringUtils.EMPTY, user);
    }
}
