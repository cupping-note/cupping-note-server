package com.penguin.cuppingnote.pref.service;

import com.penguin.cuppingnote.pref.domain.PrefRepository;
import com.penguin.cuppingnote.pref.dto.request.PrefTestRequestDto;
import com.penguin.cuppingnote.pref.dto.response.PrefTestResponseDto;
import com.penguin.cuppingnote.pref.mapper.PrefMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PrefService {

    private final PrefMapper prefMapper;
    private final PrefRepository prefRepository;

    @Transactional
    public PrefTestResponseDto getTestResult(PrefTestRequestDto prefTestRequestDto) {
        // TODO: logic 추가

        prefRepository.save(prefMapper.toPrefEntityBy(prefTestRequestDto));

        return PrefTestResponseDto.succeed(
                "https://cupping.cpglcdn.com/images/result/flavor.png"
        );
    }

}
