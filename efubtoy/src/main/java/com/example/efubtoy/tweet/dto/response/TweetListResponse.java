package com.example.efubtoy.tweet.dto.response;

import com.example.efubtoy.main.response.MainTweetResponse; // ⭐ MainTweetResponse 임포트
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TweetListResponse {
    // ⭐ 이제 List<Tweet>이 아니라 List<MainTweetResponse>를 담습니다.
    private List<MainTweetResponse> tweets;
}