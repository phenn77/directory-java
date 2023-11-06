package com.training.directory.controller;

import com.training.directory.dao.request.ForgotPasswordRequest;
import com.training.directory.dao.request.LoginRequest;
import com.training.directory.dao.request.SignUpRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseBody login(@RequestBody LoginRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("signup")
    public ResponseBody signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("forgotPassword")
    public ResponseBody forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authenticationService.
    }
}
