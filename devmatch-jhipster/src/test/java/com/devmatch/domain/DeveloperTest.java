package com.devmatch.domain;

import static com.devmatch.domain.DeveloperTestSamples.*;
import static com.devmatch.domain.ExperienceTestSamples.*;
import static com.devmatch.domain.TechnologyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.devmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DeveloperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Developer.class);
        Developer developer1 = getDeveloperSample1();
        Developer developer2 = new Developer();
        assertThat(developer1).isNotEqualTo(developer2);

        developer2.setId(developer1.getId());
        assertThat(developer1).isEqualTo(developer2);

        developer2 = getDeveloperSample2();
        assertThat(developer1).isNotEqualTo(developer2);
    }

    @Test
    void experiencesTest() {
        Developer developer = getDeveloperRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        developer.addExperiences(experienceBack);
        assertThat(developer.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getDeveloper()).isEqualTo(developer);

        developer.removeExperiences(experienceBack);
        assertThat(developer.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getDeveloper()).isNull();

        developer.experiences(new HashSet<>(Set.of(experienceBack)));
        assertThat(developer.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getDeveloper()).isEqualTo(developer);

        developer.setExperiences(new HashSet<>());
        assertThat(developer.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getDeveloper()).isNull();
    }

    @Test
    void skillsTest() {
        Developer developer = getDeveloperRandomSampleGenerator();
        Technology technologyBack = getTechnologyRandomSampleGenerator();

        developer.addSkills(technologyBack);
        assertThat(developer.getSkills()).containsOnly(technologyBack);

        developer.removeSkills(technologyBack);
        assertThat(developer.getSkills()).doesNotContain(technologyBack);

        developer.skills(new HashSet<>(Set.of(technologyBack)));
        assertThat(developer.getSkills()).containsOnly(technologyBack);

        developer.setSkills(new HashSet<>());
        assertThat(developer.getSkills()).doesNotContain(technologyBack);
    }
}
