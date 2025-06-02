package com.example.efubtoy.main.controller;

import com.example.efubtoy.main.service.MainService; // ⭐ MainService 임포트
import com.example.efubtoy.main.response.MainTweetResponse; // ⭐ MainTweetResponse 임포트
import com.example.efubtoy.global.response.CommonResponse; // CommonResponse 임포트
import com.example.efubtoy.tweet.dto.response.TweetListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main") // 당신이 사용하는 매핑 경로를 사용하세요.
public class MainController { // 또는 TweetController

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    // 메인 화면에 보여줄 모든 트윗을 가져오는 API
    @GetMapping
    public ResponseEntity<CommonResponse<TweetListResponse>> getTweetsForMainScreen() {
        // ⭐ MainService에서 getTweetsForMainFeed() 메서드를 호출합니다.
        List<MainTweetResponse> mainTweetResponses = mainService.getTweetsForMainFeed();

        // TweetListResponse 객체를 생성하여 MainTweetResponse 목록을 "tweets" 필드 안에 넣습니다.
        TweetListResponse tweetListData = new TweetListResponse(mainTweetResponses);

        // CommonResponse.success() 메서드를 사용하여 전체 응답 구조를 만듭니다.
        CommonResponse<TweetListResponse> response = CommonResponse.success(tweetListData);

        return ResponseEntity.ok(response);
    }
}