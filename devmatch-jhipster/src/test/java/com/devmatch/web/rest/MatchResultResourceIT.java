package com.devmatch.web.rest;

import static com.devmatch.domain.MatchResultAsserts.*;
import static com.devmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static com.devmatch.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devmatch.IntegrationTest;
import com.devmatch.domain.Developer;
import com.devmatch.domain.MatchResult;
import com.devmatch.domain.Project;
import com.devmatch.repository.MatchResultRepository;
import com.devmatch.service.dto.MatchResultDTO;
import com.devmatch.service.mapper.MatchResultMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MatchResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchResultResourceIT {

    private static final BigDecimal DEFAULT_TECHNICAL_MATCH = new BigDecimal(1);
    private static final BigDecimal UPDATED_TECHNICAL_MATCH = new BigDecimal(2);

    private static final Integer DEFAULT_AI_TECHNICAL_AFFINITY = 1;
    private static final Integer UPDATED_AI_TECHNICAL_AFFINITY = 2;

    private static final Integer DEFAULT_AI_MOTIVATIONAL_AFFINITY = 1;
    private static final Integer UPDATED_AI_MOTIVATIONAL_AFFINITY = 2;

    private static final Integer DEFAULT_AI_EXPERIENCE_RELEVANCE = 1;
    private static final Integer UPDATED_AI_EXPERIENCE_RELEVANCE = 2;

    private static final String DEFAULT_AI_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_AI_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/match-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchResultMapper matchResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchResultMockMvc;

    private MatchResult matchResult;

    private MatchResult insertedMatchResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchResult createEntity(EntityManager em) {
        MatchResult matchResult = new MatchResult()
            .technicalMatch(DEFAULT_TECHNICAL_MATCH)
            .aiTechnicalAffinity(DEFAULT_AI_TECHNICAL_AFFINITY)
            .aiMotivationalAffinity(DEFAULT_AI_MOTIVATIONAL_AFFINITY)
            .aiExperienceRelevance(DEFAULT_AI_EXPERIENCE_RELEVANCE)
            .aiComment(DEFAULT_AI_COMMENT)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity();
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        matchResult.setProject(project);
        // Add required entity
        Developer developer;
        if (TestUtil.findAll(em, Developer.class).isEmpty()) {
            developer = DeveloperResourceIT.createEntity();
            em.persist(developer);
            em.flush();
        } else {
            developer = TestUtil.findAll(em, Developer.class).get(0);
        }
        matchResult.setDeveloper(developer);
        return matchResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchResult createUpdatedEntity(EntityManager em) {
        MatchResult updatedMatchResult = new MatchResult()
            .technicalMatch(UPDATED_TECHNICAL_MATCH)
            .aiTechnicalAffinity(UPDATED_AI_TECHNICAL_AFFINITY)
            .aiMotivationalAffinity(UPDATED_AI_MOTIVATIONAL_AFFINITY)
            .aiExperienceRelevance(UPDATED_AI_EXPERIENCE_RELEVANCE)
            .aiComment(UPDATED_AI_COMMENT)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity();
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        updatedMatchResult.setProject(project);
        // Add required entity
        Developer developer;
        if (TestUtil.findAll(em, Developer.class).isEmpty()) {
            developer = DeveloperResourceIT.createUpdatedEntity();
            em.persist(developer);
            em.flush();
        } else {
            developer = TestUtil.findAll(em, Developer.class).get(0);
        }
        updatedMatchResult.setDeveloper(developer);
        return updatedMatchResult;
    }

    @BeforeEach
    void initTest() {
        matchResult = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedMatchResult != null) {
            matchResultRepository.delete(insertedMatchResult);
            insertedMatchResult = null;
        }
    }

    @Test
    @Transactional
    void createMatchResult() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);
        var returnedMatchResultDTO = om.readValue(
            restMatchResultMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchResultDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchResultDTO.class
        );

        // Validate the MatchResult in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchResult = matchResultMapper.toEntity(returnedMatchResultDTO);
        assertMatchResultUpdatableFieldsEquals(returnedMatchResult, getPersistedMatchResult(returnedMatchResult));

        insertedMatchResult = returnedMatchResult;
    }

    @Test
    @Transactional
    void createMatchResultWithExistingId() throws Exception {
        // Create the MatchResult with an existing ID
        matchResult.setId(1L);
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTechnicalMatchIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchResult.setTechnicalMatch(null);

        // Create the MatchResult, which fails.
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        restMatchResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchResultDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchResult.setCreatedAt(null);

        // Create the MatchResult, which fails.
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        restMatchResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchResultDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchResults() throws Exception {
        // Initialize the database
        insertedMatchResult = matchResultRepository.saveAndFlush(matchResult);

        // Get all the matchResultList
        restMatchResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].technicalMatch").value(hasItem(sameNumber(DEFAULT_TECHNICAL_MATCH))))
            .andExpect(jsonPath("$.[*].aiTechnicalAffinity").value(hasItem(DEFAULT_AI_TECHNICAL_AFFINITY)))
            .andExpect(jsonPath("$.[*].aiMotivationalAffinity").value(hasItem(DEFAULT_AI_MOTIVATIONAL_AFFINITY)))
            .andExpect(jsonPath("$.[*].aiExperienceRelevance").value(hasItem(DEFAULT_AI_EXPERIENCE_RELEVANCE)))
            .andExpect(jsonPath("$.[*].aiComment").value(hasItem(DEFAULT_AI_COMMENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getMatchResult() throws Exception {
        // Initialize the database
        insertedMatchResult = matchResultRepository.saveAndFlush(matchResult);

        // Get the matchResult
        restMatchResultMockMvc
            .perform(get(ENTITY_API_URL_ID, matchResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchResult.getId().intValue()))
            .andExpect(jsonPath("$.technicalMatch").value(sameNumber(DEFAULT_TECHNICAL_MATCH)))
            .andExpect(jsonPath("$.aiTechnicalAffinity").value(DEFAULT_AI_TECHNICAL_AFFINITY))
            .andExpect(jsonPath("$.aiMotivationalAffinity").value(DEFAULT_AI_MOTIVATIONAL_AFFINITY))
            .andExpect(jsonPath("$.aiExperienceRelevance").value(DEFAULT_AI_EXPERIENCE_RELEVANCE))
            .andExpect(jsonPath("$.aiComment").value(DEFAULT_AI_COMMENT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT));
    }

    @Test
    @Transactional
    void getNonExistingMatchResult() throws Exception {
        // Get the matchResult
        restMatchResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchResult() throws Exception {
        // Initialize the database
        insertedMatchResult = matchResultRepository.saveAndFlush(matchResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchResult
        MatchResult updatedMatchResult = matchResultRepository.findById(matchResult.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchResult are not directly saved in db
        em.detach(updatedMatchResult);
        updatedMatchResult
            .technicalMatch(UPDATED_TECHNICAL_MATCH)
            .aiTechnicalAffinity(UPDATED_AI_TECHNICAL_AFFINITY)
            .aiMotivationalAffinity(UPDATED_AI_MOTIVATIONAL_AFFINITY)
            .aiExperienceRelevance(UPDATED_AI_EXPERIENCE_RELEVANCE)
            .aiComment(UPDATED_AI_COMMENT)
            .createdAt(UPDATED_CREATED_AT);
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(updatedMatchResult);

        restMatchResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchResultDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchResultToMatchAllProperties(updatedMatchResult);
    }

    @Test
    @Transactional
    void putNonExistingMatchResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchResult.setId(longCount.incrementAndGet());

        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchResult.setId(longCount.incrementAndGet());

        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchResult.setId(longCount.incrementAndGet());

        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchResultMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchResultDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchResultWithPatch() throws Exception {
        // Initialize the database
        insertedMatchResult = matchResultRepository.saveAndFlush(matchResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchResult using partial update
        MatchResult partialUpdatedMatchResult = new MatchResult();
        partialUpdatedMatchResult.setId(matchResult.getId());

        partialUpdatedMatchResult
            .technicalMatch(UPDATED_TECHNICAL_MATCH)
            .aiTechnicalAffinity(UPDATED_AI_TECHNICAL_AFFINITY)
            .aiMotivationalAffinity(UPDATED_AI_MOTIVATIONAL_AFFINITY)
            .aiExperienceRelevance(UPDATED_AI_EXPERIENCE_RELEVANCE);

        restMatchResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchResult))
            )
            .andExpect(status().isOk());

        // Validate the MatchResult in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchResultUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchResult, matchResult),
            getPersistedMatchResult(matchResult)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchResultWithPatch() throws Exception {
        // Initialize the database
        insertedMatchResult = matchResultRepository.saveAndFlush(matchResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchResult using partial update
        MatchResult partialUpdatedMatchResult = new MatchResult();
        partialUpdatedMatchResult.setId(matchResult.getId());

        partialUpdatedMatchResult
            .technicalMatch(UPDATED_TECHNICAL_MATCH)
            .aiTechnicalAffinity(UPDATED_AI_TECHNICAL_AFFINITY)
            .aiMotivationalAffinity(UPDATED_AI_MOTIVATIONAL_AFFINITY)
            .aiExperienceRelevance(UPDATED_AI_EXPERIENCE_RELEVANCE)
            .aiComment(UPDATED_AI_COMMENT)
            .createdAt(UPDATED_CREATED_AT);

        restMatchResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchResult))
            )
            .andExpect(status().isOk());

        // Validate the MatchResult in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchResultUpdatableFieldsEquals(partialUpdatedMatchResult, getPersistedMatchResult(partialUpdatedMatchResult));
    }

    @Test
    @Transactional
    void patchNonExistingMatchResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchResult.setId(longCount.incrementAndGet());

        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchResultDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchResult.setId(longCount.incrementAndGet());

        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchResult.setId(longCount.incrementAndGet());

        // Create the MatchResult
        MatchResultDTO matchResultDTO = matchResultMapper.toDto(matchResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchResultMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(matchResultDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchResult() throws Exception {
        // Initialize the database
        insertedMatchResult = matchResultRepository.saveAndFlush(matchResult);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchResult
        restMatchResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchResultRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected MatchResult getPersistedMatchResult(MatchResult matchResult) {
        return matchResultRepository.findById(matchResult.getId()).orElseThrow();
    }

    protected void assertPersistedMatchResultToMatchAllProperties(MatchResult expectedMatchResult) {
        assertMatchResultAllPropertiesEquals(expectedMatchResult, getPersistedMatchResult(expectedMatchResult));
    }

    protected void assertPersistedMatchResultToMatchUpdatableProperties(MatchResult expectedMatchResult) {
        assertMatchResultAllUpdatablePropertiesEquals(expectedMatchResult, getPersistedMatchResult(expectedMatchResult));
    }
}
