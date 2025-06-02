package com.example.efubtoy.main.response;

import com.example.efubtoy.tweet.domain.Tweet;
import com.example.efubtoy.user.domain.User; // user.domain.User 임포트 확인
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List; // media 필드를 위한 List import

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainTweetResponse {
    private String id; // 트윗 ID (String 타입으로 통일)
    private AuthorDto author; // 작성자 정보
    private String content; // 트윗 내용
    private List<String> media; // 미디어 URL 목록 (현재 Tweet 엔티티에 없지만, 확장성을 위해 포함)
    private LocalDateTime createdAt; // 트윗 생성 시간 (LocalDateTime 타입)
    private int retweetCount;
    private int likeCount;
    private int viewCount;

    // 내부 클래스로 AuthorDto 정의
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthorDto {
        private Long userId; // user_id (Long 타입)
        private String username; // User 엔티티의 nickname을 사용 (표시명으로)
        // profileImage 필드는 User 엔티티에 없으므로 제거합니다.
    }

    // Tweet 엔티티로부터 MainTweetResponse DTO를 생성하는 정적 팩토리 메서드
    public static MainTweetResponse from(Tweet tweet) {
        User writer = tweet.getWriter();
        return MainTweetResponse.builder()
                .id(String.valueOf(tweet.getId())) // Long -> String 변환
                .author(AuthorDto.builder()
                        .userId(writer.getUserId())
                        .username(writer.getNickname()) // User 엔티티의 nickname 사용
                        .build())
                .content(tweet.getContent())
                .media(List.of()) // 현재 Tweet 엔티티에 media 필드가 없으므로 빈 리스트 반환
                .createdAt(tweet.getCreatedAt()) // BaseEntity에서 상속받은 createdAt 사용
                .retweetCount(tweet.getRetweetCount())
                .likeCount(tweet.getLikeCount())
                .viewCount(tweet.getViewCount())
                .build();
    }
}