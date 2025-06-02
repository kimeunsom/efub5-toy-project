package com.example.efubtoy.main.controller;

import com.example.efubtoy.main.response.MainTweetResponse;
import com.example.efubtoy.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main") // URI: /main
public class MainController {

    private final MainService mainService;

    // 메인 화면 트윗 조회
    // URI: GET /main
    @GetMapping
    public ResponseEntity<List<MainTweetResponse>> getTweetsOnMain() {
        List<MainTweetResponse> tweets = mainService.getTweetsForMainFeed();
        return ResponseEntity.ok(tweets); // HTTP 200 OK와 함께 트윗 목록 반환
    }
}