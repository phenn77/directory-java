package com.training.directory.service;

import com.training.directory.dao.request.LoginRequest;
import com.training.directory.dao.request.SignUpRequest;
import com.training.directory.dao.response.LoginResponse;
import com.training.directory.dao.response.ResponseBody;

public interface AuthenticationService {

    LoginResponse signIn(LoginRequest request);

    ResponseBody signUp(SignUpRequest request);
}
