package com.penguin.cuppingnote.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penguin.cuppingnote.common.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final String headerKey;
    private final Jwt jwt;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(
            final ServletRequest req,
            final ServletResponse res,
            final FilterChain chain
    ) throws IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String token = getToken(request);

        try {
            final JwtAuthenticationDto jwtAuthenticationDto = verify(token)
                    .toJwtAuthenticationDto();

            if (canSetAuthenticationBy(jwtAuthenticationDto)) {
                SecurityContextHolder.getContext().setAuthentication(
                        JwtAuthenticationToken.of(
                                token,
                                jwtAuthenticationDto,
                                request
                        )
                );
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            setErrorResponseHeaders(response);
            setErrorMessage(response, e.getMessage());
        }
    }

    private String getToken(final HttpServletRequest request) {
        final String token = request.getHeader(headerKey);

        return isNotEmpty(token) ?
                URLDecoder.decode(token, StandardCharsets.UTF_8):
                null;
    }

    private Claims verify(final String token) {
        return jwt.verify(token);
    }

    private boolean canSetAuthenticationBy(final JwtAuthenticationDto jwtAuthenticationDto) {
        return Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) &&
                !Objects.isNull(jwtAuthenticationDto.getUserId()) &&
                isNotEmpty(jwtAuthenticationDto.getEmail()) &&
                jwtAuthenticationDto.getAuthorities().size() > 0;
    }

    private void setErrorResponseHeaders(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
    }

    private void setErrorMessage(HttpServletResponse response, String message) throws IOException {
        objectMapper.writeValue(response.getWriter(), new ErrorResponse(message));
    }
}
