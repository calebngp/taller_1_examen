package com.devmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Project getProjectSample1() {
        return new Project()
            .id(1L)
            .name("name1")
            .experienceLevel("experienceLevel1")
            .projectType("projectType1")
            .status("status1")
            .usuarioCreacion("usuarioCreacion1")
            .usuarioModificacion("usuarioModificacion1");
    }

    public static Project getProjectSample2() {
        return new Project()
            .id(2L)
            .name("name2")
            .experienceLevel("experienceLevel2")
            .projectType("projectType2")
            .status("status2")
            .usuarioCreacion("usuarioCreacion2")
            .usuarioModificacion("usuarioModificacion2");
    }

    public static Project getProjectRandomSampleGenerator() {
        return new Project()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .experienceLevel(UUID.randomUUID().toString())
            .projectType(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .usuarioCreacion(UUID.randomUUID().toString())
            .usuarioModificacion(UUID.randomUUID().toString());
    }
}
