package com.training.directory.service;

import com.training.directory.dao.response.ResponseBody;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    ResponseBody getProfile();
}
