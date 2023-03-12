package com.penguin.cuppingnote.pref.service;

import com.penguin.cuppingnote.coffeebean.domain.Flavor;
import com.penguin.cuppingnote.common.exception.pref.BadPrefRequestException;
import com.penguin.cuppingnote.pref.domain.PrefRepository;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
import com.penguin.cuppingnote.pref.dto.response.PrefTestResponseDto;
import com.penguin.cuppingnote.pref.mapper.PrefMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrefService {

    private final PrefMapper prefMapper;
    private final PrefRepository prefRepository;

    @Transactional
    public PrefTestResponseDto getTestResult(PrefTestRequestDto prefTestRequestDto) {
        PrefTestResponseDto prefTestResponseDto = getResponse(prefTestRequestDto);
        prefRepository.save(prefMapper.toPrefEntityBy(prefTestRequestDto));
        return prefTestResponseDto;
    }

    private PrefTestResponseDto getResponse(PrefTestRequestDto prefTestRequestDto) {
        Map<Flavor, Integer> flavorScoreMap = fieldsToMap(prefTestRequestDto);
        int max = Collections.max(flavorScoreMap.values());

        if (max <= 2) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/balance.png"
            );
        }

        Map<Flavor, Integer> maxMap = getMaxFlavorScoreMap(max, flavorScoreMap);

        if (maxMap.size() == 1) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/" + maxMap.keySet().stream().toList().get(0).name() + ".png"
            );
        }
        // 최댓값이 복수개인 경우
        // 우선순위 : 산미 > 쓴맛 > 단맛 > 바디 > 향
        if (maxMap.containsKey(Flavor.acidity)) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/acidity.png"
            );
        } else if (maxMap.containsKey(Flavor.bitterness)) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/bitterness.png"
            );
        } else if (maxMap.containsKey(Flavor.sweetness)) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/sweetness.png"
            );
        } else if (maxMap.containsKey(Flavor.body)) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/body.png"
            );
        } else if (maxMap.containsKey(Flavor.aroma)) {
            return PrefTestResponseDto.succeed(
                    "https://cupping.cpglcdn.com/images/result/aroma.png"
            );
        } else {
            throw new BadPrefRequestException();
        }
    }

    private Map<Flavor, Integer> fieldsToMap(PrefTestRequestDto prefTestRequestDto) {
        Map<Flavor, Integer> result = new HashMap<>();
        result.put(Flavor.aroma, prefTestRequestDto.getAroma());
        result.put(Flavor.acidity, prefTestRequestDto.getAcidity());
        result.put(Flavor.sweetness, prefTestRequestDto.getSweetness());
        result.put(Flavor.bitterness, prefTestRequestDto.getBitterness());
        result.put(Flavor.body, prefTestRequestDto.getBody());
        return result;
    }

    private Map<Flavor, Integer> getMaxFlavorScoreMap(int max, Map<Flavor, Integer> flavorScoreMap) {
        Map<Flavor, Integer> maxMap = new HashMap<>();
        for (Map.Entry<Flavor, Integer> entry : flavorScoreMap.entrySet()) {
            if (entry.getValue() == max) {
                maxMap.put(entry.getKey(), entry.getValue());
            }
        }
        return maxMap;
    }

}
