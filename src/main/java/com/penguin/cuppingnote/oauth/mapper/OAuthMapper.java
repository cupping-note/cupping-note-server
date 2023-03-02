package com.penguin.cuppingnote.oauth.mapper;

import com.penguin.cuppingnote.oauth.dto.OAuthKakaoProperties;
import com.penguin.cuppingnote.oauth.dto.request.OAuthKakaoTokenRequest;
import com.penguin.cuppingnote.user.dto.request.UserKakaoLoginRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OAuthMapper {

    @Mappings({
            @Mapping(target = "grantType", source = "oAuthKakaoProperties.grantType"),
            @Mapping(target = "clientId", source = "oAuthKakaoProperties.restAPIKey"),
            @Mapping(target = "redirectUri", source = "userKakaoLoginRequestDto.redirectUri"),
            @Mapping(target = "authorizationCode", source = "userKakaoLoginRequestDto.authorizationCode"),
            @Mapping(target = "clientSecret", source = "oAuthKakaoProperties.clientSecretKey"),
    })
    OAuthKakaoTokenRequest toRequestDTOBy(OAuthKakaoProperties oAuthKakaoProperties, UserKakaoLoginRequestDto userKakaoLoginRequestDto);
}