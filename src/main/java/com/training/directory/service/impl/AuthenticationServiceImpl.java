package com.training.directory.service.impl;

import com.training.directory.model.request.LoginRequest;
import com.training.directory.model.request.SignUpRequest;
import com.training.directory.model.response.LoginResponse;
import com.training.directory.model.response.ResponseBody;
import com.training.directory.service.AuthenticationService;
import com.training.directory.service.impl.authentication.SignInServiceImpl;
import com.training.directory.service.impl.authentication.SignUpServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SignInServiceImpl signInService;
    private final SignUpServiceImpl signUpService;

    @Override
    public LoginResponse signIn(LoginRequest request) {
        return signInService.signIn(request);
    }

    @Override
    public ResponseBody signUp(SignUpRequest request) {
        return signUpService.signUp(request);
    }
}