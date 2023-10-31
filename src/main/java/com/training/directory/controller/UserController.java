package com.training.directory.controller;

import com.training.directory.model.request.LoginRequest;
import com.training.directory.model.request.SignUpRequest;
import com.training.directory.model.response.LoginResponse;
import com.training.directory.model.response.ResponseBody;
import com.training.directory.service.AuthenticationService;
import com.training.directory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

//    @GetMapping("me")
//    public UserDetails login() {
//        return
//    }
}
