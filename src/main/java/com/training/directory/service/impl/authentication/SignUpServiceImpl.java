package com.training.directory.service.impl.authentication;

import com.training.directory.constant.ApplicationConstant;
import com.training.directory.constant.ModelType;
import com.training.directory.constant.Role;
import com.training.directory.constant.Status;
import com.training.directory.dao.request.SignUpRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.exception.BusinessException;
import com.training.directory.model.User;
import com.training.directory.repository.UserRepository;
import com.training.directory.service.AuditService;
import com.training.directory.util.ApplicationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class SignUpServiceImpl {

    private final AuditService auditService;
    private final UserRepository userRepository;

    public ResponseBody process(SignUpRequest request) {
        var existingUser = userRepository.findByUsernameOrEmail(request.username(), request.email());

        if (Objects.nonNull(existingUser)) {
            log.error("User already exist. Username: {}, Email: {}", request.username(), request.email());

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
        user.setUpdatedBy(ApplicationConstant.SIGN_UP);

        var data = userRepository.saveAndFlush(user);

        auditService.auditData(
                ApplicationConstant.SIGN_UP,
                HttpMethod.POST,
                ModelType.USER,
                data.getId(),
                null,
                data);

        return new ResponseBody(Status.SUCCESS, "User creation success.", null);
    }
}
