package com.training.directory.service.impl.authentication;

import com.training.directory.constant.ApplicationConstant;
import com.training.directory.constant.Role;
import com.training.directory.constant.Status;
import com.training.directory.exception.BusinessException;
import com.training.directory.model.User;
import com.training.directory.model.request.SignUpRequest;
import com.training.directory.model.response.ResponseBody;
import com.training.directory.repository.UserRepository;
import com.training.directory.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SignUpServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseBody signUp(SignUpRequest request) {
        var existingUser = userRepository.findByUsernameOrEmail(request.username(), request.email());

        if (Objects.nonNull(existingUser)) {
            logger.error("User already exist. Username: {}, Email: {}", request.username(), request.email());

            throw new BusinessException("User already exist.");
        }

        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthday(request.birthday())
                .username(request.username())
                .email(request.email())
                .password(ApplicationUtil.hashCredential(request.password()))
                .role(Role.USER)
                .build();
        user.setCreatedBy(ApplicationConstant.SIGN_UP);

        userRepository.save(user);

        return new ResponseBody(Status.SUCCESS, "User creation success.");
    }
}
