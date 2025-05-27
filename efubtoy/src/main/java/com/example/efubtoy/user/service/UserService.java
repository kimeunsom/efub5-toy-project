package com.example.efubtoy.user.service;

import com.example.efubtoy.tweet.repository.TweetRepository; // <-- 이 경로로 수정
import com.example.efubtoy.user.domain.User;
import com.example.efubtoy.user.dto.response.UserContentDto;
import com.example.efubtoy.user.dto.response.UserProfileDto;
import com.example.efubtoy.user.dto.response.UserProfileWithContentResponseDto;
import com.example.efubtoy.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service // Spring 빈으로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동 생성하여 의존성 주입
public class UserService {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository; // 유저의 트윗을 가져오기 위함

    // 나의 프로필 조회 (프로필 정보와 작성 트윗 목록 포함)
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
    public UserProfileWithContentResponseDto getMyProfile(Long userId) {
        // 1. userId로 User 엔티티 조회 (없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. ID: " + userId));

        // 2. User 엔티티를 UserProfileDto로 변환
        UserProfileDto userProfileDto = new UserProfileDto(user);

        // 3. 해당 User가 작성한 트윗 목록 조회 및 UserContentDto 리스트로 변환
        // findByWriterOrderByCreatedAtDesc(User writer) 같은 메서드를 TweetRepository에 추가하는 것이 더 효율적입니다.
        // 이 부분은 이미 TweetRepository 수정 시 언급되었으며, UserService를 최적화된 방식으로 수정합니다.
        List<UserContentDto> userTweets = tweetRepository.findByWriterOrderByCreatedAtDesc(user).stream() // <-- 효율적인 쿼리 메서드 사용
                .map(UserContentDto::new) // Tweet 엔티티를 UserContentDto로 변환
                .collect(Collectors.toList()); // 리스트로 수집

        // 4. UserProfileDto와 UserContentDto 리스트를 담아 최종 응답 DTO 생성 및 반환
        return UserProfileWithContentResponseDto.builder()
                .user(userProfileDto)
                .content(userTweets)
                .build();
    }
}
