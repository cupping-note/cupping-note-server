package com.penguin.cuppingnote.pref.dto.response;

import com.penguin.cuppingnote.pref.domain.PrefResultType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class PrefTestResponseDto {

    @Schema(
            description = "취향 테스트 결과 타입",
            example = "balance"
    )
    private PrefResultType resultType;

    public static PrefTestResponseDto succeed(
            PrefResultType resultType
    ) {
        return PrefTestResponseDto.builder()
                .resultType(resultType)
                .build();
    }
}
