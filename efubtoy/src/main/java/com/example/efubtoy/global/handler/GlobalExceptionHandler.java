package com.example.efubtoy.global.handler; // 이 패키지 경로는 당신의 프로젝트에 맞게 수정하세요.

import com.example.efubtoy.global.exception.dto.ErrorCode; // 정의한 ErrorCode Enum 임포트
import com.example.efubtoy.global.exception.dto.GlobalException; // 정의한 GlobalException 클래스 임포트
import com.example.efubtoy.global.response.ErrResponse; // <--- 이 부분이 중요합니다! ErrResponse 클래스의 실제 패키지 경로를 사용하세요.

import lombok.extern.slf4j.Slf4j; // 로그를 사용하기 위한 Lombok 어노테이션
import org.springframework.http.ResponseEntity; // HTTP 응답을 위한 클래스
import org.springframework.web.bind.annotation.ExceptionHandler; // 특정 예외를 처리하기 위한 어노테이션
import org.springframework.web.bind.annotation.RestControllerAdvice; // 전역 예외 처리를 위한 어노테이션

@Slf4j // 이 클래스에서 로그(log.error 등)를 사용할 수 있도록 해줍니다.
@RestControllerAdvice // 이 클래스가 애플리케이션 전반에 걸쳐 컨트롤러에서 발생하는 예외를 처리하도록 선언합니다.
public class GlobalExceptionHandler {

    /**
     * 우리가 직접 정의한 GlobalException 예외를 처리하는 메서드입니다.
     * TweetService 등에서 throw new GlobalException(...)이 발생하면 이 메서드가 호출됩니다.
     *
     * @param ex 발생한 GlobalException 객체
     * @return 클라이언트에게 보낼 ResponseEntity (HTTP 상태 코드와 에러 응답 본문 포함)
     */
    @ExceptionHandler(GlobalException.class) // GlobalException 타입의 예외가 발생하면 이 메서드가 실행됩니다.
    public ResponseEntity<ErrResponse> handleGlobalException(GlobalException ex) { // <--- ErrResponse로 타입 변경
        ErrorCode errorCode = ex.getErrorCode(); // 발생한 GlobalException에서 ErrorCode를 가져옵니다.

        // 에러 로그를 남깁니다. (운영 환경에서는 민감한 정보는 제외하고 로깅)
        log.error("GlobalException 발생: [에러 코드: {} - 에러 메시지: {}] 상세: {}",
                errorCode.getCode(), errorCode.getMessage(), ex.getMessage());

        // ErrResponse 객체를 생성합니다.
        ErrResponse errorResponse = new ErrResponse(errorCode.getCode(), errorCode.getMessage()); // <--- ErrResponse로 객체 생성

        // ResponseEntity를 사용하여 HTTP 응답을 구성합니다.
        // ErrorCode에 정의된 HttpStatus (예: 404, 403)를 사용합니다.
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * GlobalException으로 처리되지 않은, 예상치 못한 모든 종류의 예외를 처리하는 메서드입니다.
     * 이는 "최후의 보루" 역할을 합니다.
     *
     * @param ex 발생한 일반 Exception 객체
     * @return 클라이언트에게 보낼 ResponseEntity (항상 500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class) // 모든 종류의 Exception이 발생하면 이 메서드가 실행됩니다.
    public ResponseEntity<ErrResponse> handleAllOtherExceptions(Exception ex) { // <--- ErrResponse로 타입 변경
        // 예상치 못한 에러이므로 상세한 로그를 남기는 것이 중요합니다.
        log.error("예상치 못한 에러 발생: {}", ex.getMessage(), ex);

        // 클라이언트에게는 일반적인 500 Internal Server Error를 반환합니다.
        // 내부 서버 에러는 보안상 상세한 정보를 노출하지 않는 것이 좋습니다.
        ErrResponse errorResponse = new ErrResponse( // <--- ErrResponse로 객체 생성
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),      // ErrorCode Enum에서 500 에러 코드 가져옴
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage()    // ErrorCode Enum에서 500 에러 메시지 가져옴
        );
        return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
    }
}