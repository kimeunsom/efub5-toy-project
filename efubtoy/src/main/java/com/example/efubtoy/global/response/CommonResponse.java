package com.example.efubtoy.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString; // 디버깅에 도움이 될 수 있습니다.

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString // 응답 내용을 쉽게 확인할 수 있도록
public class CommonResponse<T> {
    private String code;
    private String message;
    private T data; // 실제 데이터를 담을 필드 (예: TweetListResponse)

    // 성공 응답을 쉽게 생성하기 위한 정적 팩토리 메서드
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("C001", "요청 성공", data);
    }

    // 데이터 없이 성공 응답을 생성할 경우 (예: POST 요청 성공)
    public static CommonResponse<Void> success() {
        return new CommonResponse<>("C001", "요청 성공", null);
    }

    // 오류 응답을 생성하기 위한 정적 팩토리 메서드
    public static <T> CommonResponse<T> error(String code, String message) {
        return new CommonResponse<>(code, message, null);
    }

    public static <T> CommonResponse<T> error(String code, String message, T data) {
        return new CommonResponse<>(code, message, data);
    }
}