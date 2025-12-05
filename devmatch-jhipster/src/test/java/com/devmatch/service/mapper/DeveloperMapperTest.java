package com.devmatch.service.mapper;

import static com.devmatch.domain.DeveloperAsserts.*;
import static com.devmatch.domain.DeveloperTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeveloperMapperTest {

    private DeveloperMapper developerMapper;

    @BeforeEach
    void setUp() {
        developerMapper = new DeveloperMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDeveloperSample1();
        var actual = developerMapper.toEntity(developerMapper.toDto(expected));
        assertDeveloperAllPropertiesEquals(expected, actual);
    }
}
