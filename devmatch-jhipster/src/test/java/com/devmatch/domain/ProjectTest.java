package com.devmatch.domain;

import static com.devmatch.domain.ProjectTestSamples.*;
import static com.devmatch.domain.TechnologyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.devmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = getProjectSample1();
        Project project2 = new Project();
        assertThat(project1).isNotEqualTo(project2);

        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);

        project2 = getProjectSample2();
        assertThat(project1).isNotEqualTo(project2);
    }

    @Test
    void requiredTechnologiesTest() {
        Project project = getProjectRandomSampleGenerator();
        Technology technologyBack = getTechnologyRandomSampleGenerator();

        project.addRequiredTechnologies(technologyBack);
        assertThat(project.getRequiredTechnologies()).containsOnly(technologyBack);

        project.removeRequiredTechnologies(technologyBack);
        assertThat(project.getRequiredTechnologies()).doesNotContain(technologyBack);

        project.requiredTechnologies(new HashSet<>(Set.of(technologyBack)));
        assertThat(project.getRequiredTechnologies()).containsOnly(technologyBack);

        project.setRequiredTechnologies(new HashSet<>());
        assertThat(project.getRequiredTechnologies()).doesNotContain(technologyBack);
    }
}
