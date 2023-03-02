package com.penguin.cuppingnote.user.mapper;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.user.domain.session.Session;
import com.penguin.cuppingnote.user.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "email", source = "oAuthKakaoUserResponse.kakaoAccount.email"),
            @Mapping(target = "nickName", source = "oAuthKakaoUserResponse.properties.nickname"),
            @Mapping(target = "profileImageUrl", source = "oAuthKakaoUserResponse.properties.profileImage"),
            @Mapping(target = "oauthPlatform", expression = "java(com.penguin.cuppingnote.oauth.domain.OAuthPlatforms.KAKAO)"),
            @Mapping(target = "role", expression = "java(com.penguin.cuppingnote.user.domain.user.Role.USER)"),
    })
    User toEntityBy(OAuthKakaoUserResponse oAuthKakaoUserResponse);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "refreshToken", source = "authentication.refreshToken"),
            @Mapping(target = "issuesAt", expression = "java(decodedJWT.getIssuedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime())"),
            @Mapping(target = "expiresAt", expression = "java(decodedJWT.getExpiresAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime())"),
    })
    Session toSessionEntityBy(User user, JwtAuthentication authentication, DecodedJWT decodedJWT);
}
