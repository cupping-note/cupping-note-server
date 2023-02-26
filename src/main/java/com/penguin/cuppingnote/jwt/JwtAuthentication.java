package com.penguin.cuppingnote.jwt;

import com.penguin.cuppingnote.common.exception.jwt.TokenNotFoundException;
import com.penguin.cuppingnote.common.exception.jwt.UserInformationNotFoundException;

import java.util.Objects;

public class JwtAuthentication {
    public final String refreshToken;
    public final String accessToken;
    public final Long userId;
    public final String email;

    public JwtAuthentication(
            final String refreshToken,
            final String accessToken,
            final Long userId,
            final String email
    ) {
        if (Objects.isNull(accessToken))
            throw new TokenNotFoundException();
        if (Objects.isNull(userId))
            throw new UserInformationNotFoundException();
        if (Objects.isNull(email))
            throw new UserInformationNotFoundException();

        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
    }
}
