package com.penguin.cuppingnote.pref.mapper;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.pref.domain.UserPreference;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
import com.penguin.cuppingnote.user.domain.session.Session;
import com.penguin.cuppingnote.user.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PrefMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "prefTestRequestDto.userId"),
            @Mapping(target = "flavor", source = "prefTestRequestDto.flavor"),
            @Mapping(target = "acidity", source = "prefTestRequestDto.acidity"),
            @Mapping(target = "sweet", source = "prefTestRequestDto.sweet"),
            @Mapping(target = "bitter", source = "prefTestRequestDto.bitter"),
            @Mapping(target = "body", source = "prefTestRequestDto.body"),
    })
    UserPreference toPrefEntityBy(PrefTestRequestDto prefTestRequestDto);
}
