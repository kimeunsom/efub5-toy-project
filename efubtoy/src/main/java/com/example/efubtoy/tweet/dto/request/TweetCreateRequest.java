package com.example.efubtoy.tweet.dto.request;

import com.example.efubtoy.tweet.domain.Tweet;
import com.example.efubtoy.user.domain.User; // User 엔티티 임포트
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TweetCreateRequest {

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long userId; // 작성자 ID

    @NotBlank(message = "트윗 내용은 비어있을 수 없습니다.")
    @Size(max = 280, message = "트윗 내용은 280자를 초과할 수 없습니다.")
    private String content; // 트윗 내용

    private Long parentId; // 답글인 경우 부모 트윗 ID (선택 사항)

    @Builder
    public TweetCreateRequest(Long userId, String content, Long parentId) {
        this.userId = userId;
        this.content = content;
        this.parentId = parentId;
    }

    // 요청 DTO를 엔티티로 변환하는 메서드
    public Tweet toEntity(User writer) {
        return Tweet.builder()
                .content(this.content)
                .writer(writer) // Service에서 조회한 User 객체를 주입
                .parentId(this.parentId)
                .build();
    }
}