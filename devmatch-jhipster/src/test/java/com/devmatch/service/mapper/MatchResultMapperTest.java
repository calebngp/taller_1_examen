package com.devmatch.service.mapper;

import static com.devmatch.domain.MatchResultAsserts.*;
import static com.devmatch.domain.MatchResultTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchResultMapperTest {

    private MatchResultMapper matchResultMapper;

    @BeforeEach
    void setUp() {
        matchResultMapper = new MatchResultMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchResultSample1();
        var actual = matchResultMapper.toEntity(matchResultMapper.toDto(expected));
        assertMatchResultAllPropertiesEquals(expected, actual);
    }
}
