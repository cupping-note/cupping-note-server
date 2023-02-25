package com.penguin.cuppingnote.jwt;

import com.penguin.cuppingnote.common.exception.jwt.CannotSetTokenException;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private String credentials;

    public JwtAuthenticationToken(final String principal) {
        super(null);
        super.setAuthenticated(false);

        this.principal = principal;
    }

    public JwtAuthenticationToken(
            final Object principal,
            final String credentials,
            final Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new CannotSetTokenException();
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

    public static JwtAuthenticationToken of(
            final String token,
            final JwtAuthenticationDto jwtAuthenticationDto,
            final HttpServletRequest request
    ) {
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                new JwtAuthentication(token, jwtAuthenticationDto.getUserId(), jwtAuthenticationDto.getEmail()),
                null,
                jwtAuthenticationDto.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }
}
