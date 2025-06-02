// src/main/java/com/example/efubtoy/main/service/MainService.java
package com.example.efubtoy.main.service;

import com.example.efubtoy.main.repository.MainRepository;
import com.example.efubtoy.main.response.MainTweetResponse;
import com.example.efubtoy.tweet.domain.Tweet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainRepository mainRepository;

    // 메인 화면에 표시할 트윗 목록을 게시일 최신순으로 조회
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
    public List<MainTweetResponse> getTweetsForMainFeed() {
        // MainRepository를 통해 최신순으로 정렬된 트윗 엔티티 목록 조회
        List<Tweet> tweets = mainRepository.findAllByOrderByCreatedAtDesc();

        // 조회된 트윗 엔티티 목록을 MainTweetResponse DTO 목록으로 변환
        return tweets.stream()
                .map(MainTweetResponse::from) // MainTweetResponse.from() 메서드 활용
                .collect(Collectors.toList());
    }
}