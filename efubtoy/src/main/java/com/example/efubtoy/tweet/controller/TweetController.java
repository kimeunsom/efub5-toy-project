package com.example.efubtoy.tweet.controller;


import com.example.efubtoy.tweet.dto.request.TweetCreateRequest;
import com.example.efubtoy.tweet.dto.response.TweetResponse;
import com.example.efubtoy.tweet.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets") // 모든 엔드포인트에 "/tweets" 기본 경로 적용
public class TweetController {

    private final TweetService tweetService;

    // 트윗 생성
    @PostMapping
    public ResponseEntity<Void> createTweet(@Valid @RequestBody TweetCreateRequest request) {
        Long tweetId = tweetService.createTweet(request); // 메서드 이름 통일: createTweet
        // ResponseEntity.created()를 사용하여 Location 헤더에 생성된 리소스의 URI를 반환
        return ResponseEntity.created(URI.create("/tweets/" + tweetId)).build();
    }

    // 특정 트윗 1개 조회
    @GetMapping("/{tweetId}") // @PathVariable 변수 이름을 "tweetId"로 통일
    public ResponseEntity<TweetResponse> getTweet(@PathVariable("tweetId") Long tweetId) {
        TweetResponse tweetResponse = tweetService.getTweet(tweetId); // 올바른 호출 방식
        return ResponseEntity.ok(tweetResponse);
    }

    // 트윗 삭제
    @DeleteMapping("/{tweetId}") // @PathVariable 변수 이름을 "tweetId"로 통일
    public ResponseEntity<Void> deleteTweet(@PathVariable("tweetId") Long tweetId, // 메서드 이름 통일: deleteTweet
                                            @RequestHeader("Auth-Id") Long requestingUserId) { // 비밀번호 제거 (서비스 계층에서 처리)
        // 서비스 계층에서 트윗 삭제 로직 호출 (권한 검증 포함)
        tweetService.deleteTweet(tweetId, requestingUserId);
        // 성공적인 삭제는 204 No Content로 응답
        return ResponseEntity.noContent().build();
    }

    // TODO: 필요하다면 모든 트윗 조회, 트윗 수정 등의 엔드포인트 추가 가능
}