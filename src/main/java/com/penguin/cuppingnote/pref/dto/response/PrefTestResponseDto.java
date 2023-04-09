package com.penguin.cuppingnote.pref.dto.response;

import com.penguin.cuppingnote.pref.domain.PrefResultType;
import com.penguin.cuppingnote.pref.dto.RecommendCoffeeBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

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

    private List<RecommendCoffeeBean> recommendCoffeeBeans;

    public static PrefTestResponseDto succeed(
            PrefResultType resultType,
            List<RecommendCoffeeBean> recommendCoffeeBeans
    ) {
        return PrefTestResponseDto.builder()
                .resultType(resultType)
                .recommendCoffeeBeans(recommendCoffeeBeans)
                .build();
    }
}
