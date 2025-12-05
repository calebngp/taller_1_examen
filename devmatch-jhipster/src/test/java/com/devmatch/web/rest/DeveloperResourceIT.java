package com.devmatch.web.rest;

import static com.devmatch.domain.DeveloperAsserts.*;
import static com.devmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devmatch.IntegrationTest;
import com.devmatch.domain.Developer;
import com.devmatch.repository.DeveloperRepository;
import com.devmatch.service.DeveloperService;
import com.devmatch.service.dto.DeveloperDTO;
import com.devmatch.service.mapper.DeveloperMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeveloperResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DeveloperResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_PROFILE = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVATION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVATION = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO_CREACION = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_CREACION = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO_MODIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_MODIFICACION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/developers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperRepository developerRepositoryMock;

    @Autowired
    private DeveloperMapper developerMapper;

    @Mock
    private DeveloperService developerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeveloperMockMvc;

    private Developer developer;

    private Developer insertedDeveloper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developer createEntity() {
        return new Developer()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .experienceLevel(DEFAULT_EXPERIENCE_LEVEL)
            .bio(DEFAULT_BIO)
            .location(DEFAULT_LOCATION)
            .githubProfile(DEFAULT_GITHUB_PROFILE)
            .linkedin(DEFAULT_LINKEDIN)
            .motivation(DEFAULT_MOTIVATION)
            .usuarioCreacion(DEFAULT_USUARIO_CREACION)
            .usuarioModificacion(DEFAULT_USUARIO_MODIFICACION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developer createUpdatedEntity() {
        return new Developer()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .experienceLevel(UPDATED_EXPERIENCE_LEVEL)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .linkedin(UPDATED_LINKEDIN)
            .motivation(UPDATED_MOTIVATION)
            .usuarioCreacion(UPDATED_USUARIO_CREACION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
    }

    @BeforeEach
    void initTest() {
        developer = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDeveloper != null) {
            developerRepository.delete(insertedDeveloper);
            insertedDeveloper = null;
        }
    }

    @Test
    @Transactional
    void createDeveloper() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);
        var returnedDeveloperDTO = om.readValue(
            restDeveloperMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(developerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DeveloperDTO.class
        );

        // Validate the Developer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDeveloper = developerMapper.toEntity(returnedDeveloperDTO);
        assertDeveloperUpdatableFieldsEquals(returnedDeveloper, getPersistedDeveloper(returnedDeveloper));

        insertedDeveloper = returnedDeveloper;
    }

    @Test
    @Transactional
    void createDeveloperWithExistingId() throws Exception {
        // Create the Developer with an existing ID
        developer.setId(1L);
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeveloperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(developerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        developer.setName(null);

        // Create the Developer, which fails.
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        restDeveloperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(developerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDevelopers() throws Exception {
        // Initialize the database
        insertedDeveloper = developerRepository.saveAndFlush(developer);

        // Get all the developerList
        restDeveloperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(developer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].experienceLevel").value(hasItem(DEFAULT_EXPERIENCE_LEVEL)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].githubProfile").value(hasItem(DEFAULT_GITHUB_PROFILE)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].motivation").value(hasItem(DEFAULT_MOTIVATION)))
            .andExpect(jsonPath("$.[*].usuarioCreacion").value(hasItem(DEFAULT_USUARIO_CREACION)))
            .andExpect(jsonPath("$.[*].usuarioModificacion").value(hasItem(DEFAULT_USUARIO_MODIFICACION)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDevelopersWithEagerRelationshipsIsEnabled() throws Exception {
        when(developerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeveloperMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(developerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDevelopersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(developerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeveloperMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(developerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDeveloper() throws Exception {
        // Initialize the database
        insertedDeveloper = developerRepository.saveAndFlush(developer);

        // Get the developer
        restDeveloperMockMvc
            .perform(get(ENTITY_API_URL_ID, developer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(developer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.experienceLevel").value(DEFAULT_EXPERIENCE_LEVEL))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.githubProfile").value(DEFAULT_GITHUB_PROFILE))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.motivation").value(DEFAULT_MOTIVATION))
            .andExpect(jsonPath("$.usuarioCreacion").value(DEFAULT_USUARIO_CREACION))
            .andExpect(jsonPath("$.usuarioModificacion").value(DEFAULT_USUARIO_MODIFICACION))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDeveloper() throws Exception {
        // Get the developer
        restDeveloperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeveloper() throws Exception {
        // Initialize the database
        insertedDeveloper = developerRepository.saveAndFlush(developer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the developer
        Developer updatedDeveloper = developerRepository.findById(developer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeveloper are not directly saved in db
        em.detach(updatedDeveloper);
        updatedDeveloper
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .experienceLevel(UPDATED_EXPERIENCE_LEVEL)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .linkedin(UPDATED_LINKEDIN)
            .motivation(UPDATED_MOTIVATION)
            .usuarioCreacion(UPDATED_USUARIO_CREACION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        DeveloperDTO developerDTO = developerMapper.toDto(updatedDeveloper);

        restDeveloperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, developerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(developerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeveloperToMatchAllProperties(updatedDeveloper);
    }

    @Test
    @Transactional
    void putNonExistingDeveloper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        developer.setId(longCount.incrementAndGet());

        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeveloperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, developerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(developerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeveloper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        developer.setId(longCount.incrementAndGet());

        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeveloperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(developerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeveloper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        developer.setId(longCount.incrementAndGet());

        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeveloperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(developerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeveloperWithPatch() throws Exception {
        // Initialize the database
        insertedDeveloper = developerRepository.saveAndFlush(developer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the developer using partial update
        Developer partialUpdatedDeveloper = new Developer();
        partialUpdatedDeveloper.setId(developer.getId());

        partialUpdatedDeveloper
            .name(UPDATED_NAME)
            .experienceLevel(UPDATED_EXPERIENCE_LEVEL)
            .linkedin(UPDATED_LINKEDIN)
            .motivation(UPDATED_MOTIVATION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restDeveloperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeveloper.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeveloper))
            )
            .andExpect(status().isOk());

        // Validate the Developer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeveloperUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeveloper, developer),
            getPersistedDeveloper(developer)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeveloperWithPatch() throws Exception {
        // Initialize the database
        insertedDeveloper = developerRepository.saveAndFlush(developer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the developer using partial update
        Developer partialUpdatedDeveloper = new Developer();
        partialUpdatedDeveloper.setId(developer.getId());

        partialUpdatedDeveloper
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .experienceLevel(UPDATED_EXPERIENCE_LEVEL)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .linkedin(UPDATED_LINKEDIN)
            .motivation(UPDATED_MOTIVATION)
            .usuarioCreacion(UPDATED_USUARIO_CREACION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restDeveloperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeveloper.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeveloper))
            )
            .andExpect(status().isOk());

        // Validate the Developer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeveloperUpdatableFieldsEquals(partialUpdatedDeveloper, getPersistedDeveloper(partialUpdatedDeveloper));
    }

    @Test
    @Transactional
    void patchNonExistingDeveloper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        developer.setId(longCount.incrementAndGet());

        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeveloperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, developerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(developerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeveloper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        developer.setId(longCount.incrementAndGet());

        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeveloperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(developerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeveloper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        developer.setId(longCount.incrementAndGet());

        // Create the Developer
        DeveloperDTO developerDTO = developerMapper.toDto(developer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeveloperMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(developerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Developer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeveloper() throws Exception {
        // Initialize the database
        insertedDeveloper = developerRepository.saveAndFlush(developer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the developer
        restDeveloperMockMvc
            .perform(delete(ENTITY_API_URL_ID, developer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return developerRepository.count();
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

    protected Developer getPersistedDeveloper(Developer developer) {
        return developerRepository.findById(developer.getId()).orElseThrow();
    }

    protected void assertPersistedDeveloperToMatchAllProperties(Developer expectedDeveloper) {
        assertDeveloperAllPropertiesEquals(expectedDeveloper, getPersistedDeveloper(expectedDeveloper));
    }

    protected void assertPersistedDeveloperToMatchUpdatableProperties(Developer expectedDeveloper) {
        assertDeveloperAllUpdatablePropertiesEquals(expectedDeveloper, getPersistedDeveloper(expectedDeveloper));
    }
}
