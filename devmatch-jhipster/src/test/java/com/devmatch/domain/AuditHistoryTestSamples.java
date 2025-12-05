package com.devmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AuditHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AuditHistory getAuditHistorySample1() {
        return new AuditHistory().id(1L).entityType("entityType1").entityId(1L).fieldName("fieldName1").usuario("usuario1");
    }

    public static AuditHistory getAuditHistorySample2() {
        return new AuditHistory().id(2L).entityType("entityType2").entityId(2L).fieldName("fieldName2").usuario("usuario2");
    }

    public static AuditHistory getAuditHistoryRandomSampleGenerator() {
        return new AuditHistory()
            .id(longCount.incrementAndGet())
            .entityType(UUID.randomUUID().toString())
            .entityId(longCount.incrementAndGet())
            .fieldName(UUID.randomUUID().toString())
            .usuario(UUID.randomUUID().toString());
    }
}
