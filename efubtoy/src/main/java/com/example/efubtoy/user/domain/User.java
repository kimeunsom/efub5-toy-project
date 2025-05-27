package com.example.efubtoy.user.domain;

import com.example.efubtoy.global.domain.BaseEntity; // BaseEntity 임포트
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users") // 실제 DB 테이블 이름이 "users"인지 확인하세요.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity { // BaseEntity 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // user ID (기본키)

    @Column(nullable = false)
    private String username; // 유저 이름

    @Column(nullable = false, unique = true)
    private String nickname; // 유저 닉네임 (unique)

    @Column(nullable = false)
    private Long followingCount; // 팔로잉 수

    @Column(nullable = false)
    private Long followerCount; // 팔로워 수

    @Column(nullable = false)
    private boolean isVerified; // 멤버십 여부 (파란딱지)

    @Column(length = 255)
    private String bio; // 한 줄 소개 (BTO)

    @Builder
    public User(String username, String nickname, String bio) {
        this.username = username;
        this.nickname = nickname;
        this.bio = (bio == null || bio.trim().isEmpty()) ? "" : bio; // bio 초기값 설정: null 또는 공백이면 빈 문자열
        this.followingCount = 0L; // 초기값 0
        this.followerCount = 0L; // 초기값 0
        this.isVerified = false; // 초기값 false (멤버십 없음)
    }

    // 팔로잉 수 증가 메서드
    public void addFollowing() {
        this.followingCount++;
    }

    // 팔로잉 수 감소 메서드
    public void removeFollowing() {
        if (this.followingCount > 0) { // 0 미만으로 내려가지 않도록 방지
            this.followingCount--;
        }
    }

    // 팔로워 수 증가 메서드
    public void addFollower() {
        this.followerCount++;
    }

    // 팔로워 수 감소 메서드
    public void removeFollower() {
        if (this.followerCount > 0) { // 0 미만으로 내려가지 않도록 방지
            this.followerCount--;
        }
    }
}