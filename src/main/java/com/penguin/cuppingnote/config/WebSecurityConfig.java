package com.penguin.cuppingnote.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penguin.cuppingnote.jwt.Jwt;
import com.penguin.cuppingnote.jwt.JwtAuthenticationFilter;
import com.penguin.cuppingnote.jwt.JwtAuthenticationProvider;
import com.penguin.cuppingnote.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/index.html",
                        "/webjars/**",
                        "/swagger-ui/**"
                );
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Object principal = !Objects.isNull(authentication)
                    ? authentication.getPrincipal()
                    : null;
            log.error("{} is denied", principal, e);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("ACCESS DENIED");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    @Bean
    public Jwt jwt() {
        return new Jwt(
                jwtConfig.getIssuer(),
                jwtConfig.getClientSecret(),
                jwtConfig.getRefreshTokenExpirySeconds(),
                jwtConfig.getAccessTokenExpirySeconds()
        );
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(
            final UserRepository userRepository,
            final Jwt jwt
    ) {
        return new JwtAuthenticationProvider(jwt, userRepository);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(
                jwtConfig.getHeader(),
                getApplicationContext().getBean(Jwt.class),
                objectMapper
        );
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/user/kakao/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/token").permitAll()
                    .antMatchers(HttpMethod.GET, "/swagger").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .headers()
                    .disable()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .rememberMe()
                    .disable()
                .logout()
                    .disable()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class)
        ;
    }
}
