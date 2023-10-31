package com.training.directory.controller;

import com.training.directory.model.request.LoginRequest;
import com.training.directory.model.request.SignUpRequest;
import com.training.directory.model.response.LoginResponse;
import com.training.directory.model.response.ResponseBody;
import com.training.directory.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("signup")
    public ResponseBody signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }
}
