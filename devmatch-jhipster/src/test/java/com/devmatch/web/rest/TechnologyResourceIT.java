package com.devmatch.web.rest;

import static com.devmatch.domain.TechnologyAsserts.*;
import static com.devmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devmatch.IntegrationTest;
import com.devmatch.domain.Technology;
import com.devmatch.repository.TechnologyRepository;
import com.devmatch.service.dto.TechnologyDTO;
import com.devmatch.service.mapper.TechnologyMapper;
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
 * Integration tests for the {@link TechnologyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TechnologyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/technologies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyMapper technologyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    private Technology insertedTechnology;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createEntity() {
        return new Technology().name(DEFAULT_NAME).category(DEFAULT_CATEGORY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createUpdatedEntity() {
        return new Technology().name(UPDATED_NAME).category(UPDATED_CATEGORY);
    }

    @BeforeEach
    void initTest() {
        technology = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTechnology != null) {
            technologyRepository.delete(insertedTechnology);
            insertedTechnology = null;
        }
    }

    @Test
    @Transactional
    void createTechnology() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);
        var returnedTechnologyDTO = om.readValue(
            restTechnologyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(technologyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TechnologyDTO.class
        );

        // Validate the Technology in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTechnology = technologyMapper.toEntity(returnedTechnologyDTO);
        assertTechnologyUpdatableFieldsEquals(returnedTechnology, getPersistedTechnology(returnedTechnology));

        insertedTechnology = returnedTechnology;
    }

    @Test
    @Transactional
    void createTechnologyWithExistingId() throws Exception {
        // Create the Technology with an existing ID
        technology.setId(1L);
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnologyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(technologyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        technology.setName(null);

        // Create the Technology, which fails.
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        restTechnologyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(technologyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTechnologies() throws Exception {
        // Initialize the database
        insertedTechnology = technologyRepository.saveAndFlush(technology);

        // Get all the technologyList
        restTechnologyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }

    @Test
    @Transactional
    void getTechnology() throws Exception {
        // Initialize the database
        insertedTechnology = technologyRepository.saveAndFlush(technology);

        // Get the technology
        restTechnologyMockMvc
            .perform(get(ENTITY_API_URL_ID, technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(technology.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    void getNonExistingTechnology() throws Exception {
        // Get the technology
        restTechnologyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTechnology() throws Exception {
        // Initialize the database
        insertedTechnology = technologyRepository.saveAndFlush(technology);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the technology
        Technology updatedTechnology = technologyRepository.findById(technology.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTechnology are not directly saved in db
        em.detach(updatedTechnology);
        updatedTechnology.name(UPDATED_NAME).category(UPDATED_CATEGORY);
        TechnologyDTO technologyDTO = technologyMapper.toDto(updatedTechnology);

        restTechnologyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, technologyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(technologyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTechnologyToMatchAllProperties(updatedTechnology);
    }

    @Test
    @Transactional
    void putNonExistingTechnology() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        technology.setId(longCount.incrementAndGet());

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnologyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, technologyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(technologyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTechnology() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        technology.setId(longCount.incrementAndGet());

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnologyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(technologyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTechnology() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        technology.setId(longCount.incrementAndGet());

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnologyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(technologyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTechnologyWithPatch() throws Exception {
        // Initialize the database
        insertedTechnology = technologyRepository.saveAndFlush(technology);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the technology using partial update
        Technology partialUpdatedTechnology = new Technology();
        partialUpdatedTechnology.setId(technology.getId());

        restTechnologyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTechnology.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTechnology))
            )
            .andExpect(status().isOk());

        // Validate the Technology in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTechnologyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTechnology, technology),
            getPersistedTechnology(technology)
        );
    }

    @Test
    @Transactional
    void fullUpdateTechnologyWithPatch() throws Exception {
        // Initialize the database
        insertedTechnology = technologyRepository.saveAndFlush(technology);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the technology using partial update
        Technology partialUpdatedTechnology = new Technology();
        partialUpdatedTechnology.setId(technology.getId());

        partialUpdatedTechnology.name(UPDATED_NAME).category(UPDATED_CATEGORY);

        restTechnologyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTechnology.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTechnology))
            )
            .andExpect(status().isOk());

        // Validate the Technology in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTechnologyUpdatableFieldsEquals(partialUpdatedTechnology, getPersistedTechnology(partialUpdatedTechnology));
    }

    @Test
    @Transactional
    void patchNonExistingTechnology() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        technology.setId(longCount.incrementAndGet());

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnologyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, technologyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(technologyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTechnology() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        technology.setId(longCount.incrementAndGet());

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnologyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(technologyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTechnology() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        technology.setId(longCount.incrementAndGet());

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.toDto(technology);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTechnologyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(technologyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Technology in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTechnology() throws Exception {
        // Initialize the database
        insertedTechnology = technologyRepository.saveAndFlush(technology);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the technology
        restTechnologyMockMvc
            .perform(delete(ENTITY_API_URL_ID, technology.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return technologyRepository.count();
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

    protected Technology getPersistedTechnology(Technology technology) {
        return technologyRepository.findById(technology.getId()).orElseThrow();
    }

    protected void assertPersistedTechnologyToMatchAllProperties(Technology expectedTechnology) {
        assertTechnologyAllPropertiesEquals(expectedTechnology, getPersistedTechnology(expectedTechnology));
    }

    protected void assertPersistedTechnologyToMatchUpdatableProperties(Technology expectedTechnology) {
        assertTechnologyAllUpdatablePropertiesEquals(expectedTechnology, getPersistedTechnology(expectedTechnology));
    }
}
