package com.penguin.cuppingnote.oauth.dto.resonse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.penguin.cuppingnote.oauth.domain.OAuthPlatforms;
import com.penguin.cuppingnote.user.domain.user.Role;
import com.penguin.cuppingnote.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kakao 사용자 정보 반환 DTO
 * docs url : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info-response
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OAuthKakaoUserResponse {
    private Long id;
    private LocalDateTime connectedAt;
    private Boolean hasSignedUp;
    private LocalDateTime synchedAt;
    private Properties properties;
    private KakaoAccount kakaoAccount;
    private Partner forPartner;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        private String profileNeedsAgreement;
        private String profileNicknameNeedsAgreement;
        private String profileImageNeedsAgreement;
        private Profile profile;
        private Boolean nameNeedsAgreement;
        private String name;
        private Boolean emailNeedsAgreement;
        private Boolean isEmailValid;
        private Boolean isEmailVerified;
        private String email;
        private Boolean hasEmail;
        private Boolean ageRangeNeedsAgreement;
        private String ageRange;
        private Boolean birthyearNeedsAgreement;
        private String birthyear;
        private Boolean birthdayNeedsAgreement;
        private String birthday;
        private String birthdayType;
        private Boolean genderNeedsAgreement;
        private String gender;
        private Boolean phoneNumberNeedsAgreement;
        private String phoneNumber;
        private Boolean ciNeedsAgreement;
        private String ci;
        private LocalDateTime ciAuthenticatedAt;

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
        public static class Profile {
            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private Boolean isDefaultImage;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Partner {
        private String uuid;
    }

    public User toUserEntity() {
        return User.builder()
                .email(this.kakaoAccount.email)
                .nickName(this.properties.nickname)
                .profileImageUrl(this.properties.profileImage)
                .oauthPlatform(OAuthPlatforms.KAKAO)
                .role(Role.USER)
                .build();
    }
}
