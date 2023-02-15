package com.penguin.cuppingnote.oauth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthPlatforms {
    KAKAO("kakao"),
    APPLE("apple");

    private final String value;
}
