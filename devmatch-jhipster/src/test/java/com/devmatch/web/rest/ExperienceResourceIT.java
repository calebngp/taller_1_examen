package com.devmatch.web.rest;

import static com.devmatch.domain.ExperienceAsserts.*;
import static com.devmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devmatch.IntegrationTest;
import com.devmatch.domain.Developer;
import com.devmatch.domain.Experience;
import com.devmatch.repository.ExperienceRepository;
import com.devmatch.service.dto.ExperienceDTO;
import com.devmatch.service.mapper.ExperienceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link ExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExperienceResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExperienceMockMvc;

    private Experience experience;

    private Experience insertedExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createEntity(EntityManager em) {
        Experience experience = new Experience().description(DEFAULT_DESCRIPTION).category(DEFAULT_CATEGORY);
        // Add required entity
        Developer developer;
        if (TestUtil.findAll(em, Developer.class).isEmpty()) {
            developer = DeveloperResourceIT.createEntity();
            em.persist(developer);
            em.flush();
        } else {
            developer = TestUtil.findAll(em, Developer.class).get(0);
        }
        experience.setDeveloper(developer);
        return experience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createUpdatedEntity(EntityManager em) {
        Experience updatedExperience = new Experience().description(UPDATED_DESCRIPTION).category(UPDATED_CATEGORY);
        // Add required entity
        Developer developer;
        if (TestUtil.findAll(em, Developer.class).isEmpty()) {
            developer = DeveloperResourceIT.createUpdatedEntity();
            em.persist(developer);
            em.flush();
        } else {
            developer = TestUtil.findAll(em, Developer.class).get(0);
        }
        updatedExperience.setDeveloper(developer);
        return updatedExperience;
    }

    @BeforeEach
    void initTest() {
        experience = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedExperience != null) {
            experienceRepository.delete(insertedExperience);
            insertedExperience = null;
        }
    }

    @Test
    @Transactional
    void createExperience() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);
        var returnedExperienceDTO = om.readValue(
            restExperienceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experienceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ExperienceDTO.class
        );

        // Validate the Experience in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedExperience = experienceMapper.toEntity(returnedExperienceDTO);
        assertExperienceUpdatableFieldsEquals(returnedExperience, getPersistedExperience(returnedExperience));

        insertedExperience = returnedExperience;
    }

    @Test
    @Transactional
    void createExperienceWithExistingId() throws Exception {
        // Create the Experience with an existing ID
        experience.setId(1L);
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExperiences() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        // Get all the experienceList
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }

    @Test
    @Transactional
    void getExperience() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExperience() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the experience
        Experience updatedExperience = experienceRepository.findById(experience.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExperience are not directly saved in db
        em.detach(updatedExperience);
        updatedExperience.description(UPDATED_DESCRIPTION).category(UPDATED_CATEGORY);
        ExperienceDTO experienceDTO = experienceMapper.toDto(updatedExperience);

        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, experienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(experienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExperienceToMatchAllProperties(updatedExperience);
    }

    @Test
    @Transactional
    void putNonExistingExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, experienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experienceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExperienceWithPatch() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the experience using partial update
        Experience partialUpdatedExperience = new Experience();
        partialUpdatedExperience.setId(experience.getId());

        partialUpdatedExperience.category(UPDATED_CATEGORY);

        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExperienceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExperience, experience),
            getPersistedExperience(experience)
        );
    }

    @Test
    @Transactional
    void fullUpdateExperienceWithPatch() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the experience using partial update
        Experience partialUpdatedExperience = new Experience();
        partialUpdatedExperience.setId(experience.getId());

        partialUpdatedExperience.description(UPDATED_DESCRIPTION).category(UPDATED_CATEGORY);

        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExperienceUpdatableFieldsEquals(partialUpdatedExperience, getPersistedExperience(partialUpdatedExperience));
    }

    @Test
    @Transactional
    void patchNonExistingExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, experienceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(experienceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExperience() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the experience
        restExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, experience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return experienceRepository.count();
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

    protected Experience getPersistedExperience(Experience experience) {
        return experienceRepository.findById(experience.getId()).orElseThrow();
    }

    protected void assertPersistedExperienceToMatchAllProperties(Experience expectedExperience) {
        assertExperienceAllPropertiesEquals(expectedExperience, getPersistedExperience(expectedExperience));
    }

    protected void assertPersistedExperienceToMatchUpdatableProperties(Experience expectedExperience) {
        assertExperienceAllUpdatablePropertiesEquals(expectedExperience, getPersistedExperience(expectedExperience));
    }
}
