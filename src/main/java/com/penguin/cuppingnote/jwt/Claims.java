package com.penguin.cuppingnote.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Claims {
    private Long userId;
    private String email;
    private String[] roles;
    private Date iat;
    private Date exp;

    public Claims(final DecodedJWT decodedJWT) {
        final Claim userId = decodedJWT.getClaim("user_id");
        if (!userId.isNull()) {
            this.userId = userId.asLong();
        }

        final Claim email = decodedJWT.getClaim("email");
        if (!email.isNull()) {
            this.email = email.asString();
        }

        final Claim roles = decodedJWT.getClaim("roles");
        if (!roles.isNull()) {
            this.roles = roles.asArray(String.class);
        }

        this.iat = decodedJWT.getIssuedAt();
        this.exp = decodedJWT.getExpiresAt();
    }

    public static Claims from(final Long userId, final String email, final String[] roles) {
        final Claims claims = new Claims();

        claims.userId = userId;
        claims.email = email;
        claims.roles = roles;

        return claims;
    }

    public JwtAuthenticationDto toJwtAuthenticationDto() {
        return JwtAuthenticationDto.builder()
                .userId(userId)
                .email(email)
                .authorities(getAuthorities())
                .build();
    }

    private List<GrantedAuthority> getAuthorities() {
        return Objects.isNull(roles) || roles.length == 0
                ? emptyList()
                : Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
