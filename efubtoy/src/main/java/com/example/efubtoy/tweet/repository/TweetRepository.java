package com.example.efubtoy.tweet.repository;

import com.example.efubtoy.tweet.domain.Tweet;
import com.example.efubtoy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring Data JPA 리포지토리로 지정
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    // 모든 트윗을 최신순으로 정렬
    // (UserService에서 모든 트윗을 가져와 필터링하는 대신 이 메서드를 사용하면 더 효율적)
    List<Tweet> findAllByOrderByCreatedAtDesc();

    // 특정 User가 작성한 트윗을 최신순으로 정렬
    List<Tweet> findByWriterOrderByCreatedAtDesc(User writer);

    // 이외 추가 메소드ㅡ.. 특정 트윗 ID를 가진 답글 조회
    // List<Tweet> findByParentIdOrderByCreatedAtDesc(Long parentId);
}