package com.penguin.cuppingnote.pref.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrefTestRequestDto {

    @Schema(
            description = "유저 id",
            example = "1",
            required = true
    )
    @NotNull(message = "user id가 입력되지 않았습니다.")
    private Long userId;

    @Schema(
            description = "향",
            example = "1",
            required = true
    )
    @NotNull(message = "향이 입력되지 않았습니다.")
    private Integer aroma;

    @Schema(
            description = "산미",
            example = "1",
            required = true
    )
    @NotNull(message = "산미가 입력되지 않았습니다.")
    private Integer acidity;

    @Schema(
            description = "당도",
            example = "1",
            required = true
    )
    @NotNull(message = "당도가 입력되지 않았습니다.")
    private Integer sweetness;

    @Schema(
            description = "쓴맛",
            example = "1",
            required = true
    )
    @NotNull(message = "쓴맛이 입력되지 않았습니다.")
    private Integer bitterness;

    @Schema(
            description = "바디감",
            example = "1",
            required = true
    )
    @NotNull(message = "바디감이 입력되지 않았습니다.")
    private Integer body;
}
