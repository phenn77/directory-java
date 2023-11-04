package com.training.directory.controller;

import com.training.directory.annotation.Authorized;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Authorized
    @GetMapping("me")
    public ResponseBody login() {
        return userService.getProfile();
    }
}
