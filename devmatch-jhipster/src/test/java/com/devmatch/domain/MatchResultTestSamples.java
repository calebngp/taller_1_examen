package com.devmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MatchResultTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchResult getMatchResultSample1() {
        return new MatchResult().id(1L).aiTechnicalAffinity(1).aiMotivationalAffinity(1).aiExperienceRelevance(1).createdAt("createdAt1");
    }

    public static MatchResult getMatchResultSample2() {
        return new MatchResult().id(2L).aiTechnicalAffinity(2).aiMotivationalAffinity(2).aiExperienceRelevance(2).createdAt("createdAt2");
    }

    public static MatchResult getMatchResultRandomSampleGenerator() {
        return new MatchResult()
            .id(longCount.incrementAndGet())
            .aiTechnicalAffinity(intCount.incrementAndGet())
            .aiMotivationalAffinity(intCount.incrementAndGet())
            .aiExperienceRelevance(intCount.incrementAndGet())
            .createdAt(UUID.randomUUID().toString());
    }
}
