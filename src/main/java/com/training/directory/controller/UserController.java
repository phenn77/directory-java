package com.training.directory.controller;

import com.training.directory.annotation.Authorized;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Authorized
    @GetMapping("me")
    public ResponseBody me() {
        return userService.getProfile();
    }

    @Authorized
    @PutMapping("edit")
    public ResponseBody edit(@RequestParam(required = false, name = "image") MultipartFile image) {
        return userService.editProfile(image);
    }
}
