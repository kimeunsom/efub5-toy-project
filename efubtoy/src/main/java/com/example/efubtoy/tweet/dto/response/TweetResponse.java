package com.example.efubtoy.tweet.dto.response;

import com.example.efubtoy.tweet.domain.Tweet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TweetResponse {
    private Long tweetId;
    private String content;
    private Long writerId; // 작성자 ID
    private String writerNickname; // 작성자 닉네임
    private Long parentId; // 답글일 경우 부모 트윗 ID
    private int retweetCount;
    private int likeCount;
    private int viewCount;
    private LocalDateTime createdAt;
    // List<String> media 필드는 현재 Tweet 엔티티에 없으므로 생략

    @Builder
    public TweetResponse(Long tweetId, String content, Long writerId, String writerNickname, Long parentId, int retweetCount, int likeCount, int viewCount, LocalDateTime createdAt) {
        this.tweetId = tweetId;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.parentId = parentId;
        this.retweetCount = retweetCount;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    // 엔티티를 DTO로 변환하는 정적 팩토리 메서드
    public static TweetResponse from(Tweet tweet) {
        return TweetResponse.builder()
                .tweetId(tweet.getId())
                .content(tweet.getContent())
                .writerId(tweet.getWriter().getUserId()) // User 객체에서 ID 가져옴
                .writerNickname(tweet.getWriter().getNickname()) // User 객체에서 닉네임 가져옴
                .parentId(tweet.getParentId())
                .retweetCount(tweet.getRetweetCount())
                .likeCount(tweet.getLikeCount())
                .viewCount(tweet.getViewCount())
                .createdAt(tweet.getCreatedAt())
                .build();
    }
}