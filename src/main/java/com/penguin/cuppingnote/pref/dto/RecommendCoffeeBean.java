package com.penguin.cuppingnote.pref.dto;

import com.penguin.cuppingnote.coffeebean.domain.CoffeeBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RecommendCoffeeBean {
    @Schema(
            description = "추천 원두"
    )
    private CoffeeBean coffeeBean;


    @Schema(
            description = "추천 일치율",
            example = "97.0"
    )
    private Double similarity;
}
