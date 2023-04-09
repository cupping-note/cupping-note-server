package com.penguin.cuppingnote.pref.service;

import com.penguin.cuppingnote.coffeebean.domain.CoffeeBean;
import com.penguin.cuppingnote.coffeebean.domain.Flavor;
import com.penguin.cuppingnote.common.exception.pref.BadPrefRequestException;
import com.penguin.cuppingnote.pref.domain.RecommendCoffeeBeanRepository;
import com.penguin.cuppingnote.pref.domain.PrefRepository;
import com.penguin.cuppingnote.pref.domain.PrefResultType;
import com.penguin.cuppingnote.pref.dto.RecommendCoffeeBean;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
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
    private final RecommendCoffeeBeanRepository recommendCoffeeBeanRepository;

    public static final int MAX_DISTANCE = 25;
    public static final int RECOMMEND_SIZE = 2;

    @Transactional
    public PrefResultType getTestResult(PrefTestRequestDto prefTestRequestDto) {
        PrefResultType resultType = getPrefResultType(prefTestRequestDto);
        prefRepository.save(prefMapper.toPrefEntityBy(prefTestRequestDto, resultType.getTypeText()));
        return resultType;
    }

    private PrefResultType getPrefResultType(PrefTestRequestDto prefTestRequestDto) {
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

        result.values().forEach(score -> {
            if (!isValidScore(score)) throw new BadPrefRequestException();
        });
        return result;
    }

    private boolean isValidScore(Integer score) {
        return score >= 0 && score <= 5;
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

    @Transactional
    public List<RecommendCoffeeBean> getRecommendCoffeeBeans(PrefTestRequestDto prefTestRequestDto) {
        List<CoffeeBean> coffeeBeans = recommendCoffeeBeanRepository.findSimilarCoffeeBeansWithDistance(prefTestRequestDto.getAroma(), prefTestRequestDto.getAcidity(), prefTestRequestDto.getSweetness(), prefTestRequestDto.getBitterness(), prefTestRequestDto.getBody());
        return calculateSimilarity(prefTestRequestDto, makeRecommendCoffeeBeansWithExactSize(coffeeBeans));
    }

    private List<CoffeeBean> makeRecommendCoffeeBeansWithExactSize(List<CoffeeBean> coffeeBeans) {
        if (coffeeBeans.isEmpty() || coffeeBeans.size() < RECOMMEND_SIZE) {
            coffeeBeans.addAll(recommendCoffeeBeanRepository.findTwoRandomCoffeeBeans());
        }
        coffeeBeans = coffeeBeans.subList(0, RECOMMEND_SIZE);
        return coffeeBeans;
    }

    private static List<RecommendCoffeeBean> calculateSimilarity(PrefTestRequestDto prefTestRequestDto, List<CoffeeBean> coffeeBeans) {
        List<RecommendCoffeeBean> recommendCoffeeBeans = new ArrayList<>();
        for (CoffeeBean coffeeBean : coffeeBeans) {
            int distance = Math.abs(prefTestRequestDto.getAroma() - coffeeBean.getAroma())
                    + Math.abs(prefTestRequestDto.getAcidity() - coffeeBean.getAcidity())
                    + Math.abs(prefTestRequestDto.getSweetness() - coffeeBean.getSweetness())
                    + Math.abs(prefTestRequestDto.getBitterness() - coffeeBean.getBitterness())
                    + Math.abs(prefTestRequestDto.getBody() - coffeeBean.getBody());

            Double similarity = 100.0 - ((double)distance / MAX_DISTANCE) * 100.0;
            recommendCoffeeBeans.add(new RecommendCoffeeBean(coffeeBean, similarity));
        }
        return recommendCoffeeBeans;
    }

}
