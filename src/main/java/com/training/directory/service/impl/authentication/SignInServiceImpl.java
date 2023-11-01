package com.training.directory.service.impl.authentication;

import com.training.directory.exception.BusinessException;
import com.training.directory.middleware.CredentialManager;
import com.training.directory.dao.request.LoginRequest;
import com.training.directory.dao.response.LoginResponse;
import com.training.directory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CredentialManager credentialManager;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> {
                    logger.error("Invalid username. Username: {}", request.username());
                    return new BusinessException("Invalid username.");
                });

        var jwt = credentialManager.generateToken(user);

        return new LoginResponse(jwt);
    }
}
