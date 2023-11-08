package com.training.directory.service.impl.authentication;

import com.training.directory.constant.Status;
import com.training.directory.dao.request.ForgotPasswordRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.exception.BusinessException;
import com.training.directory.repository.UserRepository;
import com.training.directory.util.ApplicationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ForgotPasswordServiceImpl {

    private final UserRepository userRepository;

    public ResponseBody process(ForgotPasswordRequest request) {
        var user = userRepository.findByUsernameOrEmail(request.username(), request.username());

        if (Objects.isNull(user)) {
            log.error("User not found. Request: {}", request.username());

            throw new BusinessException("User not found.");
        }

        user.setPassword(ApplicationUtil.hashCredential(request.password()));

        userRepository.saveAndFlush(user);

        return new ResponseBody(Status.SUCCESS, "Password changed successfully", null);
    }
}
