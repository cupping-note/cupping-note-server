package com.penguin.cuppingnote.user.domain;

import com.penguin.cuppingnote.common.domain.BaseEntity;
import com.penguin.cuppingnote.oauth.domain.OAuthPlatforms;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE user_id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    @Comment("사용자 ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    @Comment("사용자 이메일")
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    @Comment("사용자 닉네임")
    private String nickName;

    @Column(name = "profile_image_url")
    @Comment("프로필 사진 url")
    private String profileImageUrl;

    @Column(name = "oauth_platform", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("사용자 로그인한 sns 플랫폼")
    private OAuthPlatforms oauthPlatform;
}
