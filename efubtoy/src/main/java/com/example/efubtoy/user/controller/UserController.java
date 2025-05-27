package com.example.efubtoy.user.controller;

import com.example.efubtoy.user.dto.response.UserProfileWithContentResponseDto;
import com.example.efubtoy.user.service.UserService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 프로필 조회
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileWithContentResponseDto> getMyProfile(@PathVariable Long userId) {
        UserProfileWithContentResponseDto responseDto = userService.getMyProfile(userId);
        return ResponseEntity.ok(responseDto);
    }
}