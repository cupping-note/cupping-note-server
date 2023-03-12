package com.penguin.cuppingnote.pref.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class PrefTestResponseDto {
    @Schema(
            description = "결과 이미지 url",
            example = "https://cupping.cpglcdn.com/images/result/flavor.png"
    )
    private String resultImageUrl;

    public static PrefTestResponseDto succeed(
            String resultImageUrl
    ) {
        return PrefTestResponseDto.builder()
                .resultImageUrl(resultImageUrl)
                .build();
    }
}
