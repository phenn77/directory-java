package com.training.directory.service.impl;

import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.repository.UserRepository;
import com.training.directory.service.UserService;
import com.training.directory.service.impl.user.EditUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApiContext apiContext;
    private final EditUserServiceImpl editUserService;

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
        return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, apiContext.getUser());
    }

    @Override
    public ResponseBody editProfile(MultipartFile image) {
        return editUserService.process(image);
    }
}
