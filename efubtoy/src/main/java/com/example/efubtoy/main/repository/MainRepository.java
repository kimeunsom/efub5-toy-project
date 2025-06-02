package com.example.efubtoy.main.repository; // 패키지 경로는 그대로 두겠습니다.

import com.example.efubtoy.tweet.domain.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring Data JPA 리포지토리로 지정
public interface MainRepository extends JpaRepository<Tweet, Long> {
    // 이제 이 인터페이스는 Tweet 엔티티에 대한 리포지토리입니다.
    // 기존 TweetRepository에 있는 메서드 중 메인 화면에 필요한 것들을 여기에 정의하거나,
    // MainRepository에만 필요한 새로운 쿼리 메서드를 정의할 수 있습니다.

    // 예시: (기존 TweetRepository에도 동일한 메서드가 있다면 중복이 될 수 있습니다.
    // 보통은 하나의 엔티티에 하나의 JpaRepository를 만듭니다.)
    List<Tweet> findAllByOrderByCreatedAtDesc();

    // 만약 메인 화면에 필요한 특별한 트윗 쿼리가 있다면 여기에 추가합니다.
    // List<Tweet> findTop10ByOrderByLikeCountDesc(); // 예시: 좋아요 수 상위 10개 트윗
}