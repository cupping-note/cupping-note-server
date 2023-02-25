package com.penguin.cuppingnote.jwt;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class JwtAuthenticationDto {
    private Long userId;
    private String email;
    private List<GrantedAuthority> authorities;
}
