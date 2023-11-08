package com.training.directory.service.impl.authentication;

import com.training.directory.constant.Status;
import com.training.directory.dao.request.LoginRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.exception.BusinessException;
import com.training.directory.middleware.CredentialManager;
import com.training.directory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class SignInServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final CredentialManager credentialManager;
    private final UserRepository userRepository;

    public ResponseBody process(LoginRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            var user = userRepository.findByUsername(request.username())
                    .orElseThrow(() -> {
                        log.error("Invalid username. Username: {}", request.username());
                        return new BusinessException("Invalid username.");
                    });

            var token = credentialManager.generateToken(user);

            return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, Map.of("token", token));
        } catch (AuthenticationException e) {
            log.error("Authentication Error. Error: {}", (Object) ExceptionUtils.getRootCauseStackTrace(e));
            throw new BusinessException("Authentication Error");
        }
    }
}
