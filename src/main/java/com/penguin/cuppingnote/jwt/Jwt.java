package com.penguin.cuppingnote.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.penguin.cuppingnote.common.exception.jwt.ExpiredTokenException;
import com.penguin.cuppingnote.common.exception.jwt.InvalidTokenException;
import com.penguin.cuppingnote.common.exception.jwt.TokenNotFoundException;
import lombok.Getter;

import java.util.Date;

@Getter
public class Jwt {
    private final String issuer;
    private final String clientSecret;
    private final int refreshTokenExpirySeconds;
    private final int accessTokenExpirySeconds;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public Jwt(
            final String issuer,
            final String clientSecret,
            final int refreshTokenExpirySeconds,
            final int accessTokenExpirySeconds
    ) {
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.refreshTokenExpirySeconds = refreshTokenExpirySeconds;
        this.accessTokenExpirySeconds = accessTokenExpirySeconds;
        this.algorithm = Algorithm.HMAC512(clientSecret);
        this.jwtVerifier = com.auth0.jwt.JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String signRefreshToken(final Claims claims) {
        return issueJwt(claims, refreshTokenExpirySeconds);
    }

    public String signAccessToken(final Claims claims) {
        return issueJwt(claims, accessTokenExpirySeconds);
    }

    private String issueJwt(
            final Claims claims,
            int expirySeconds
    ) {
        final Date now = new Date();

        final JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(now);
        if (expirySeconds > 0) {
            builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
        }
        builder.withClaim("user_id", claims.getUserId());
        builder.withClaim("email", claims.getEmail());
        builder.withArrayClaim("roles", claims.getRoles());

        return builder.sign(algorithm);
    }

    public Claims verify(final String token) throws JWTVerificationException{
        try {
            return new Claims(jwtVerifier.verify(token));
        } catch (final TokenExpiredException e) {
            throw new ExpiredTokenException();
        } catch (final JWTVerificationException e) {
            throw new InvalidTokenException();
        } catch (final NullPointerException e) {
            throw new TokenNotFoundException();
        }
    }
}
