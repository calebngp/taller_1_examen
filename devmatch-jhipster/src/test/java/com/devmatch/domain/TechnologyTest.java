package com.devmatch.domain;

import static com.devmatch.domain.DeveloperTestSamples.*;
import static com.devmatch.domain.ProjectTestSamples.*;
import static com.devmatch.domain.TechnologyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.devmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TechnologyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Technology.class);
        Technology technology1 = getTechnologySample1();
        Technology technology2 = new Technology();
        assertThat(technology1).isNotEqualTo(technology2);

        technology2.setId(technology1.getId());
        assertThat(technology1).isEqualTo(technology2);

        technology2 = getTechnologySample2();
        assertThat(technology1).isNotEqualTo(technology2);
    }

    @Test
    void developersTest() {
        Technology technology = getTechnologyRandomSampleGenerator();
        Developer developerBack = getDeveloperRandomSampleGenerator();

        technology.addDevelopers(developerBack);
        assertThat(technology.getDevelopers()).containsOnly(developerBack);
        assertThat(developerBack.getSkills()).containsOnly(technology);

        technology.removeDevelopers(developerBack);
        assertThat(technology.getDevelopers()).doesNotContain(developerBack);
        assertThat(developerBack.getSkills()).doesNotContain(technology);

        technology.developers(new HashSet<>(Set.of(developerBack)));
        assertThat(technology.getDevelopers()).containsOnly(developerBack);
        assertThat(developerBack.getSkills()).containsOnly(technology);

        technology.setDevelopers(new HashSet<>());
        assertThat(technology.getDevelopers()).doesNotContain(developerBack);
        assertThat(developerBack.getSkills()).doesNotContain(technology);
    }

    @Test
    void projectsTest() {
        Technology technology = getTechnologyRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        technology.addProjects(projectBack);
        assertThat(technology.getProjects()).containsOnly(projectBack);
        assertThat(projectBack.getRequiredTechnologies()).containsOnly(technology);

        technology.removeProjects(projectBack);
        assertThat(technology.getProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getRequiredTechnologies()).doesNotContain(technology);

        technology.projects(new HashSet<>(Set.of(projectBack)));
        assertThat(technology.getProjects()).containsOnly(projectBack);
        assertThat(projectBack.getRequiredTechnologies()).containsOnly(technology);

        technology.setProjects(new HashSet<>());
        assertThat(technology.getProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getRequiredTechnologies()).doesNotContain(technology);
    }
}
