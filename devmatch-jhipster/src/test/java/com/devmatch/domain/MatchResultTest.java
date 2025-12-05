package com.devmatch.domain;

import static com.devmatch.domain.DeveloperTestSamples.*;
import static com.devmatch.domain.MatchResultTestSamples.*;
import static com.devmatch.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.devmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchResult.class);
        MatchResult matchResult1 = getMatchResultSample1();
        MatchResult matchResult2 = new MatchResult();
        assertThat(matchResult1).isNotEqualTo(matchResult2);

        matchResult2.setId(matchResult1.getId());
        assertThat(matchResult1).isEqualTo(matchResult2);

        matchResult2 = getMatchResultSample2();
        assertThat(matchResult1).isNotEqualTo(matchResult2);
    }

    @Test
    void projectTest() {
        MatchResult matchResult = getMatchResultRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        matchResult.setProject(projectBack);
        assertThat(matchResult.getProject()).isEqualTo(projectBack);

        matchResult.project(null);
        assertThat(matchResult.getProject()).isNull();
    }

    @Test
    void developerTest() {
        MatchResult matchResult = getMatchResultRandomSampleGenerator();
        Developer developerBack = getDeveloperRandomSampleGenerator();

        matchResult.setDeveloper(developerBack);
        assertThat(matchResult.getDeveloper()).isEqualTo(developerBack);

        matchResult.developer(null);
        assertThat(matchResult.getDeveloper()).isNull();
    }
}
