package com.example.efubtoy.tweet.domain;

import com.example.efubtoy.global.domain.BaseEntity;
import com.example.efubtoy.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tweet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tweet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 트윗 ID (기본키)

    @Column(nullable = false, length = 280) // 트윗 내용은 280자 제한 (트위터 기준)
    private String content; // 트윗 내용

    // User 엔티티와 다대일(ManyToOne) 관계 설정: 여러 개의 트윗이 한 명의 User에 의해 작성될 수 있음
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정: 트윗을 조회할 때 작성자 User 객체를 바로 로딩하지 않음
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 컬럼명 지정 (users 테이블의 user_id 참조)
    private User writer; // 트윗 작성자 (User 엔티티)

    @Column(nullable = true) // 부모 트윗 ID (답글일 경우)
    private Long parentId;

    @Column(nullable = false)
    private int retweetCount; // 리트윗 수

    @Column(nullable = false)
    private int likeCount; // 좋아요 수

    @Column(nullable = false)
    private int viewCount; // 조회수

    // media 필드는 현재는 없지만, 필요시 List<String> 등으로 추가 가능

    @Builder // 빌더 패턴을 사용하여 객체 생성
    public Tweet(String content, User writer, Long parentId) {
        this.content = content;
        this.writer = writer;
        this.parentId = parentId; // 답글이 아닐 경우 null
        this.retweetCount = 0; // 초기값 0
        this.likeCount = 0;    // 초기값 0
        this.viewCount = 0;    // 초기값 0
    }

    // 조회수 증가 메서드
    public void increaseViewCount() {
        this.viewCount++;
    }

    // 좋아요 수 증가/감소
    public void addLike() {
        this.likeCount++;
    }

    public void removeLike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    // 리트윗 수 증가/감소
    public void addRetweet() {
        this.retweetCount++;
    }

    public void removeRetweet() {
        if (this.retweetCount > 0) {
            this.retweetCount--;
        }
    }
}
