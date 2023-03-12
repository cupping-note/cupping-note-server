package com.penguin.cuppingnote.pref.service;

import com.penguin.cuppingnote.coffeebean.domain.Flavor;
import com.penguin.cuppingnote.common.exception.pref.BadPrefRequestException;
import com.penguin.cuppingnote.pref.domain.PrefRepository;
import com.penguin.cuppingnote.pref.domain.PrefResultType;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
import com.penguin.cuppingnote.pref.dto.response.PrefTestResponseDto;
import com.penguin.cuppingnote.pref.mapper.PrefMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PrefService {

    private final PrefMapper prefMapper;
    private final PrefRepository prefRepository;

    @Transactional
    public PrefTestResponseDto getTestResult(PrefTestRequestDto prefTestRequestDto) {
        PrefResultType resultType = getResponseType(prefTestRequestDto);
        PrefTestResponseDto prefTestResponseDto = PrefTestResponseDto.succeed(resultType);
        prefRepository.save(prefMapper.toPrefEntityBy(prefTestRequestDto, resultType.getTypeText()));
        return prefTestResponseDto;
    }

    private PrefResultType getResponseType(PrefTestRequestDto prefTestRequestDto) {
        Map<Flavor, Integer> flavorScoreMap = fieldsToMap(prefTestRequestDto);
        int max = Collections.max(flavorScoreMap.values());

        if (max <= 2) {
            return PrefResultType.BALANCE;
        }

        Map<Flavor, Integer> maxMap = getMaxFlavorScoreMap(max, flavorScoreMap);

        if (maxMap.size() == 1) {
            return prefMapper.toPrefResultTypeBy(maxMap.keySet().stream().toList().get(0));
        }
        // 최댓값이 복수개인 경우
        // 우선순위 : 산미 > 쓴맛 > 단맛 > 바디 > 향
        if (maxMap.containsKey(Flavor.ACIDITY)) {
            return PrefResultType.ACIDITY;
        } else if (maxMap.containsKey(Flavor.BITTERNESS)) {
            return PrefResultType.BITTERNESS;
        } else if (maxMap.containsKey(Flavor.SWEETNESS)) {
            return PrefResultType.SWEETNESS;
        } else if (maxMap.containsKey(Flavor.BODY)) {
            return PrefResultType.BODY;
        } else if (maxMap.containsKey(Flavor.AROMA)) {
            return PrefResultType.AROMA;
        } else {
            throw new BadPrefRequestException();
        }
    }

    private Map<Flavor, Integer> fieldsToMap(PrefTestRequestDto prefTestRequestDto) {
        Map<Flavor, Integer> result = new HashMap<>();
        result.put(Flavor.AROMA, prefTestRequestDto.getAroma());
        result.put(Flavor.ACIDITY, prefTestRequestDto.getAcidity());
        result.put(Flavor.SWEETNESS, prefTestRequestDto.getSweetness());
        result.put(Flavor.BITTERNESS, prefTestRequestDto.getBitterness());
        result.put(Flavor.BODY, prefTestRequestDto.getBody());
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
