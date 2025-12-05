package com.devmatch.service.mapper;

import static com.devmatch.domain.AuditHistoryAsserts.*;
import static com.devmatch.domain.AuditHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditHistoryMapperTest {

    private AuditHistoryMapper auditHistoryMapper;

    @BeforeEach
    void setUp() {
        auditHistoryMapper = new AuditHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAuditHistorySample1();
        var actual = auditHistoryMapper.toEntity(auditHistoryMapper.toDto(expected));
        assertAuditHistoryAllPropertiesEquals(expected, actual);
    }
}
