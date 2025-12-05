package com.devmatch.service.mapper;

import static com.devmatch.domain.TechnologyAsserts.*;
import static com.devmatch.domain.TechnologyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TechnologyMapperTest {

    private TechnologyMapper technologyMapper;

    @BeforeEach
    void setUp() {
        technologyMapper = new TechnologyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTechnologySample1();
        var actual = technologyMapper.toEntity(technologyMapper.toDto(expected));
        assertTechnologyAllPropertiesEquals(expected, actual);
    }
}
