package com.example.efubtoy.user.dto.response;

import com.example.efubtoy.user.domain.User; // User 엔티티 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter; // 날짜 포맷팅을 위해 추가

@Getter
@Setter
@NoArgsConstructor
public class UserProfileResponseDto { // UserProfileResponseDto로 명명

    private String userId; // 응답 예시에서는 "@user_id" 형태이므로 String으로 변경
    private String nickname; // 닉네임
    private String joinDate; // 가입일. "YYYY-MM-DD" 형태로 포맷팅
    private String bio; // 소개글
    private Long followingCount; // 팔로잉 수
    private Long followerCount; // 팔로워 수

    public UserProfileResponseDto(User user) {
        this.userId = "@" + user.getUserId(); // "@" 앞에 붙이기
        this.nickname = user.getNickname();
        this.joinDate = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // YYYY-MM-DD 포맷
        this.bio = user.getBio();
        this.followingCount = user.getFollowingCount();
        this.followerCount = user.getFollowerCount();
    }
}
