package com.example.efubtoy.user.dto.response;

import com.example.efubtoy.tweet.domain.Tweet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserContentDto {
    private String tweetId; // 트윗 ID (String 형태)
    private String content; // 트윗 내용
    private List<String> media; // 미디어 URL 리스트 (현재 Tweet 엔티티에 없으므로 빈 리스트)
    private LocalDateTime createdAt; // 트윗 생성 시간 (timestamp)
    private int retweetCount; // 리트윗 수
    private int likeCount;    // 좋아요 수
    private int viewCount;    // 조회수

    public UserContentDto(Tweet tweet) {
        this.tweetId = String.valueOf(tweet.getId()); // Long 타입 ID를 String으로 변환
        this.content = tweet.getContent();
        this.media = Collections.emptyList(); // Tweet 엔티티에 media 필드가 없으므로 일단 빈 리스트로 초기화
        // 실제 미디어 기능을 구현하려면 Tweet 엔티티에 해당 필드 추가 필요
        this.createdAt = tweet.getCreatedAt();
        this.retweetCount = tweet.getRetweetCount();
        this.likeCount = tweet.getLikeCount();
        this.viewCount = tweet.getViewCount();
    }
}