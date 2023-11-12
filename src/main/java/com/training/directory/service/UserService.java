package com.training.directory.service;

import com.training.directory.dao.response.ResponseBody;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDetailsService userDetailsService();

    ResponseBody getProfile();

    ResponseBody editProfile(MultipartFile image);
}
