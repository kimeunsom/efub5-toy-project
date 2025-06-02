package com.example.efubtoy.main.repository;

import com.example.efubtoy.tweet.domain.Tweet;
import com.example.efubtoy.tweet.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MainRepository {

    private final TweetRepository tweetRepository;

    // 모든 트윗을 게시일(createdAt) 기준으로 내림차순(최신순)으로 조회
    // Tweet 엔티티의 createdAt 필드는 BaseEntity에 정의되어 있으므로 사용 가능
    public List<Tweet> findAllTweetsOrderByCreatedAtDesc() {
        return tweetRepository.findAllByOrderByCreatedAtDesc();
    }
}