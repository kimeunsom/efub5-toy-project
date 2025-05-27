package com.example.efubtoy.tweet.service;

import com.example.efubtoy.global.exception.dto.ErrorCode;
import com.example.efubtoy.global.exception.dto.GlobalException;
import com.example.efubtoy.tweet.domain.Tweet;
import com.example.efubtoy.tweet.dto.request.TweetCreateRequest;
import com.example.efubtoy.tweet.dto.response.TweetResponse;
import com.example.efubtoy.tweet.repository.TweetRepository; // TweetRepository 임포트
import com.example.efubtoy.user.domain.User; // User 엔티티 임포트
import com.example.efubtoy.user.repository.UserRepository; // UserRepository 임포트

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository; // User 엔티티를 조회하기 위해 UserRepository 주입

    // 트윗 생성
    @Transactional
    public Long createTweet(TweetCreateRequest tweetCreateRequest) {
        // 1. 작성자 User 객체 조회
        User writerUser = userRepository.findById(tweetCreateRequest.getUserId())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND)); // User Not Found 예외 처리

        // 2. TweetCreateRequest DTO를 Tweet 엔티티로 변환하여 저장
        Tweet newTweet = tweetCreateRequest.toEntity(writerUser);
        tweetRepository.save(newTweet);
        return newTweet.getId();
    }

    // 트윗 상세 조회 (조회수 증가 포함)
    @Transactional
    public TweetResponse getTweet(Long tweetId) {
        Tweet tweet = findTweetById(tweetId); // 트윗 조회
        tweet.increaseViewCount(); // 조회수 증가 (Tweet 엔티티 내 메서드 활용)
        // tweetRepository.save(tweet); // @Transactional이 붙어있으므로 변경 감지되어 자동 저장됨
        return TweetResponse.from(tweet); // TweetResponse DTO로 변환하여 반환
    }

    // 트윗 삭제
    @Transactional
    public void deleteTweet(Long tweetId, Long requestingUserId) { // 비밀번호 검증은 로그인/인증 로직에서 분리
        Tweet tweet = findTweetById(tweetId); // 트윗 조회

        // 1. 요청 사용자가 트윗의 작성자인지 확인
        if (!tweet.getWriter().getUserId().equals(requestingUserId)) {
            throw new GlobalException(ErrorCode.TWEET_AUTHOR_MISMATCH); // 작성자 불일치 예외 처리
        }

        // 2. 트윗 삭제
        tweetRepository.delete(tweet);
    }

    // ID로 트윗을 찾고, 없으면 예외 발생시키는 내부 헬퍼 메서드
    @Transactional(readOnly = true) // 이 메서드만 단독으로 호출될 경우를 대비하여 readOnly 설정
    public Tweet findTweetById(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new GlobalException(ErrorCode.TWEET_NOT_FOUND)); // Tweet Not Found 예외 처리
    }
}