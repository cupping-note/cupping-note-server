package com.penguin.cuppingnote.user.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {
    private String token;
    private Long userId;

    public static UserLoginResponseDto succeed(
            String token,
            Long userId
    ) {
        return UserLoginResponseDto.builder()
                .token(token)
                .userId(userId)
                .build();
    }
}
