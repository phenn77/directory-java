package com.training.directory.service;

import com.training.directory.model.request.LoginRequest;
import com.training.directory.model.request.SignUpRequest;
import com.training.directory.model.response.LoginResponse;
import com.training.directory.model.response.ResponseBody;

public interface AuthenticationService {

    LoginResponse signIn(LoginRequest request);

    ResponseBody signUp(SignUpRequest request);
}
