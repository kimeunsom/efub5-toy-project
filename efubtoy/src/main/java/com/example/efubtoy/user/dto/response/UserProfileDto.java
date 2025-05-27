package com.example.efubtoy.user.dto.response;

import com.example.efubtoy.user.domain.User; // User 엔티티 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter; // 날짜 포맷팅을 위해 추가

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDto {
    private String userId; // 응답 예시에서 "@user_id" 형태였으므로 String으로 변경
    private String nickname;
    private String joinDate; // "YYYY-MM-DD" 형태로 포맷팅
    private String bio;
    // User 엔티티에서 제거되었으므로, DTO에서도 관련 필드를 제거하거나 null로 초기화합니다.
    // JSON 응답 예시에는 profileImage, headerImage가 있었으므로, 임시로 null로 설정합니다.
    private String profileImage;
    private String headerImage;
    private Long followingCount; // Long 타입 유지
    private Long followerCount;  // Long 타입 유지

    public UserProfileDto(User user) {
        // User 엔티티의 getId()가 Long 타입이므로, 이를 String으로 변환하고 "@" 접두사 추가 (응답 예시에 맞춤)
        this.userId = "@" + user.getUserId();
        this.nickname = user.getNickname();
        // user.getCreatedAt()은 LocalDateTime이므로, DateTimeFormatter를 사용해 YYYY-MM-DD 포맷으로 변환
        this.joinDate = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.bio = user.getBio();
        // User 엔티티에 해당 필드가 없으므로, 현재는 null로 설정합니다.
        // 만약 이미지 URL을 다시 User 엔티티에 추가한다면, user.getProfileImageUrl(), user.getHeaderImageUrl() 등으로 변경하세요.
        this.profileImage = null;
        this.headerImage = null;
        this.followingCount = user.getFollowingCount();
        this.followerCount = user.getFollowerCount();
    }
}