package com.penguin.cuppingnote.pref.controller;

import com.penguin.cuppingnote.pref.domain.PrefResultType;
import com.penguin.cuppingnote.pref.dto.RecommendCoffeeBean;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
import com.penguin.cuppingnote.pref.dto.response.PrefTestResponseDto;
import com.penguin.cuppingnote.pref.service.PrefService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"취향 테스트 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/pref")
public class PrefController {
    private final PrefService prefService;

    @Operation(summary = "취향 테스트 결과", description = "취향 테스트 결과를 보여줍니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "취향 테스트 결과 생성 완료", content = @Content(schema = @Schema(implementation = PrefTestResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "입력 값 중 하나 이상이 없는 경우")
    })
    @PostMapping("/result")
    public ResponseEntity<PrefTestResponseDto> getTestResult(@Valid @RequestBody PrefTestRequestDto prefTestRequestDto) {
        PrefResultType prefResultType = prefService.getTestResult(prefTestRequestDto);
        List<RecommendCoffeeBean> recommendCoffeeBeans = prefService.getRecommendCoffeeBeans(prefTestRequestDto);
        return ResponseEntity.ok(
                PrefTestResponseDto.succeed(prefResultType, recommendCoffeeBeans)
        );
    }

}
