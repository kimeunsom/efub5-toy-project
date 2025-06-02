package com.example.efubtoy.global.response;

import lombok.Getter; // Lombok의 @Getter 어노테이션을 사용하기 위해 임포트

@Getter // Lombok이 자동으로 getter 메서드를 생성해줍니다.
public class ErrResponse { // 클래스 이름: ErrResponse
    private final String code;    // 에러 코드 (예: "T001", "U001")
    private final String message; // 사용자에게 보여줄 에러 메시지 (예: "Tweet not found")

    // 생성자 이름도 클래스 이름과 동일하게 ErrResponse로 수정합니다.
    public ErrResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}