package com.devmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeveloperTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Developer getDeveloperSample1() {
        return new Developer()
            .id(1L)
            .name("name1")
            .email("email1")
            .experienceLevel("experienceLevel1")
            .location("location1")
            .githubProfile("githubProfile1")
            .linkedin("linkedin1")
            .usuarioCreacion("usuarioCreacion1")
            .usuarioModificacion("usuarioModificacion1");
    }

    public static Developer getDeveloperSample2() {
        return new Developer()
            .id(2L)
            .name("name2")
            .email("email2")
            .experienceLevel("experienceLevel2")
            .location("location2")
            .githubProfile("githubProfile2")
            .linkedin("linkedin2")
            .usuarioCreacion("usuarioCreacion2")
            .usuarioModificacion("usuarioModificacion2");
    }

    public static Developer getDeveloperRandomSampleGenerator() {
        return new Developer()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .experienceLevel(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString())
            .githubProfile(UUID.randomUUID().toString())
            .linkedin(UUID.randomUUID().toString())
            .usuarioCreacion(UUID.randomUUID().toString())
            .usuarioModificacion(UUID.randomUUID().toString());
    }
}
