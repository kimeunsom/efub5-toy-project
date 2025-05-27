package com.example.efubtoy.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileWithContentResponseDto {

    private UserProfileDto user; // 사용자 프로필 정보를 담는 DTO
    private List<UserContentDto> content; // 해당 사용자가 작성한 트윗 목록

    @Builder
    public UserProfileWithContentResponseDto(UserProfileDto user, List<UserContentDto> content) {
        this.user = user;
        this.content = content;
    }
}