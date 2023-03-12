package com.penguin.cuppingnote.pref.mapper;

import com.penguin.cuppingnote.coffeebean.domain.Flavor;
import com.penguin.cuppingnote.pref.domain.PrefResultType;
import com.penguin.cuppingnote.pref.domain.UserPreference;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PrefMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "prefTestRequestDto.userId"),
            @Mapping(target = "aroma", source = "prefTestRequestDto.aroma"),
            @Mapping(target = "acidity", source = "prefTestRequestDto.acidity"),
            @Mapping(target = "sweetness", source = "prefTestRequestDto.sweetness"),
            @Mapping(target = "bitterness", source = "prefTestRequestDto.bitterness"),
            @Mapping(target = "body", source = "prefTestRequestDto.body"),
            @Mapping(target = "resultType", source = "prefResultType"),
    })
    UserPreference toPrefEntityBy(PrefTestRequestDto prefTestRequestDto, String prefResultType);

    @ValueMappings({
            @ValueMapping(source = "AROMA", target = "AROMA"),
            @ValueMapping(source = "ACIDITY", target = "ACIDITY"),
            @ValueMapping(source = "SWEETNESS", target = "SWEETNESS"),
            @ValueMapping(source = "BITTERNESS", target = "BITTERNESS"),
            @ValueMapping(source = "BODY", target = "BODY")
    })
    PrefResultType toPrefResultTypeBy(Flavor flavor);
}
