package com.devmatch.domain;

import static com.devmatch.domain.DeveloperTestSamples.*;
import static com.devmatch.domain.ExperienceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.devmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExperienceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experience.class);
        Experience experience1 = getExperienceSample1();
        Experience experience2 = new Experience();
        assertThat(experience1).isNotEqualTo(experience2);

        experience2.setId(experience1.getId());
        assertThat(experience1).isEqualTo(experience2);

        experience2 = getExperienceSample2();
        assertThat(experience1).isNotEqualTo(experience2);
    }

    @Test
    void developerTest() {
        Experience experience = getExperienceRandomSampleGenerator();
        Developer developerBack = getDeveloperRandomSampleGenerator();

        experience.setDeveloper(developerBack);
        assertThat(experience.getDeveloper()).isEqualTo(developerBack);

        experience.developer(null);
        assertThat(experience.getDeveloper()).isNull();
    }
}
