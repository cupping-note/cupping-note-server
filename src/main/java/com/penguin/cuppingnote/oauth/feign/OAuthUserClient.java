package com.penguin.cuppingnote.oauth.feign;

import com.penguin.cuppingnote.oauth.dto.OAuthKakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "oauth-entity-client", url = "${oauth.kakao.user-url}")
public interface OAuthUserClient {

    @GetMapping("/v2/user/me")
    OAuthKakaoUserResponse getKakaoUserEntity(@RequestHeader(value = "Authorization") String authorization);
}
