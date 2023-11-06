package com.training.directory.service.impl;

import com.training.directory.dao.request.ForgotPasswordRequest;
import com.training.directory.dao.request.LoginRequest;
import com.training.directory.dao.request.SignUpRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.service.AuthenticationService;
import com.training.directory.service.impl.authentication.ForgotPasswordServiceImpl;
import com.training.directory.service.impl.authentication.SignInServiceImpl;
import com.training.directory.service.impl.authentication.SignUpServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ForgotPasswordServiceImpl forgotPasswordService;
    private final SignInServiceImpl signInService;
    private final SignUpServiceImpl signUpService;

    @Override
    public ResponseBody signIn(LoginRequest request) {
        return signInService.process(request);
    }

    @Override
    public ResponseBody signUp(SignUpRequest request) {
        return signUpService.process(request);
    }

    @Override
    public ResponseBody forgotPassword(ForgotPasswordRequest request) {
        return forgotPasswordService.process(request);
    }
}
