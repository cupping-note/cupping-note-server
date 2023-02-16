package com.penguin.cuppingnote.jwt;

import com.penguin.cuppingnote.common.exception.user.UserNotFoundException;
import com.penguin.cuppingnote.user.domain.User;
import com.penguin.cuppingnote.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;
    private final UserRepository userRepository;

    @Override
    public boolean supports(final Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;

        return processUserAuthentication(
                String.valueOf(jwtAuthentication.getPrincipal()),
                jwtAuthentication.getCredentials()
        );
    }

    private Authentication processUserAuthentication(
            final String principal,
            final String credentials
    ) {
        try {
            final User user = userRepository.findByEmail(principal)
                    .orElseThrow(UserNotFoundException::new);

            final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().getValue()));

            final String token = getToken(user.getId(), user.getEmail(), authorities);

            final JwtAuthenticationToken authenticated = new JwtAuthenticationToken(
                    new JwtAuthentication(token, user.getId(), user.getEmail()),
                    null,
                    authorities
            );
            authenticated.setDetails(user);

            return authenticated;
        } catch (final IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (final DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    private String getToken(
            final Long userId,
            final String email,
            final List<GrantedAuthority> authorities
    ) {
        final String[] roles = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        return jwt.sign(Claims.from(userId, email, roles));
    }
}
