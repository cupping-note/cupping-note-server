package com.penguin.cuppingnote.oauth.feign;

import com.penguin.cuppingnote.oauth.dto.request.OAuthKakaoTokenRequest;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "oauth-token-client", url = "${oauth.kakao.auth-url}")
public interface OAuthTokenClient {

    @PostMapping("/oauth/token")
    OAuthKakaoTokenResponse getOAuthKakaoToken(@SpringQueryMap OAuthKakaoTokenRequest request);
}
