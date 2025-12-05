package com.devmatch.web.rest;

import static com.devmatch.domain.AuditHistoryAsserts.*;
import static com.devmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devmatch.IntegrationTest;
import com.devmatch.domain.AuditHistory;
import com.devmatch.repository.AuditHistoryRepository;
import com.devmatch.service.dto.AuditHistoryDTO;
import com.devmatch.service.mapper.AuditHistoryMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AuditHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditHistoryResourceIT {

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OLD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OLD_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/audit-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AuditHistoryRepository auditHistoryRepository;

    @Autowired
    private AuditHistoryMapper auditHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditHistoryMockMvc;

    private AuditHistory auditHistory;

    private AuditHistory insertedAuditHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditHistory createEntity() {
        return new AuditHistory()
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID)
            .fieldName(DEFAULT_FIELD_NAME)
            .oldValue(DEFAULT_OLD_VALUE)
            .newValue(DEFAULT_NEW_VALUE)
            .usuario(DEFAULT_USUARIO)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditHistory createUpdatedEntity() {
        return new AuditHistory()
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .fieldName(UPDATED_FIELD_NAME)
            .oldValue(UPDATED_OLD_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .usuario(UPDATED_USUARIO)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
    }

    @BeforeEach
    void initTest() {
        auditHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAuditHistory != null) {
            auditHistoryRepository.delete(insertedAuditHistory);
            insertedAuditHistory = null;
        }
    }

    @Test
    @Transactional
    void createAuditHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);
        var returnedAuditHistoryDTO = om.readValue(
            restAuditHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AuditHistoryDTO.class
        );

        // Validate the AuditHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAuditHistory = auditHistoryMapper.toEntity(returnedAuditHistoryDTO);
        assertAuditHistoryUpdatableFieldsEquals(returnedAuditHistory, getPersistedAuditHistory(returnedAuditHistory));

        insertedAuditHistory = returnedAuditHistory;
    }

    @Test
    @Transactional
    void createAuditHistoryWithExistingId() throws Exception {
        // Create the AuditHistory with an existing ID
        auditHistory.setId(1L);
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntityTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        auditHistory.setEntityType(null);

        // Create the AuditHistory, which fails.
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        restAuditHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntityIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        auditHistory.setEntityId(null);

        // Create the AuditHistory, which fails.
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        restAuditHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFieldNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        auditHistory.setFieldName(null);

        // Create the AuditHistory, which fails.
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        restAuditHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsuarioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        auditHistory.setUsuario(null);

        // Create the AuditHistory, which fails.
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        restAuditHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaModificacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        auditHistory.setFechaModificacion(null);

        // Create the AuditHistory, which fails.
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        restAuditHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuditHistories() throws Exception {
        // Initialize the database
        insertedAuditHistory = auditHistoryRepository.saveAndFlush(auditHistory);

        // Get all the auditHistoryList
        restAuditHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].oldValue").value(hasItem(DEFAULT_OLD_VALUE)))
            .andExpect(jsonPath("$.[*].newValue").value(hasItem(DEFAULT_NEW_VALUE)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }

    @Test
    @Transactional
    void getAuditHistory() throws Exception {
        // Initialize the database
        insertedAuditHistory = auditHistoryRepository.saveAndFlush(auditHistory);

        // Get the auditHistory
        restAuditHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, auditHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditHistory.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.oldValue").value(DEFAULT_OLD_VALUE))
            .andExpect(jsonPath("$.newValue").value(DEFAULT_NEW_VALUE))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAuditHistory() throws Exception {
        // Get the auditHistory
        restAuditHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuditHistory() throws Exception {
        // Initialize the database
        insertedAuditHistory = auditHistoryRepository.saveAndFlush(auditHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the auditHistory
        AuditHistory updatedAuditHistory = auditHistoryRepository.findById(auditHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAuditHistory are not directly saved in db
        em.detach(updatedAuditHistory);
        updatedAuditHistory
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .fieldName(UPDATED_FIELD_NAME)
            .oldValue(UPDATED_OLD_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .usuario(UPDATED_USUARIO)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(updatedAuditHistory);

        restAuditHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(auditHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAuditHistoryToMatchAllProperties(updatedAuditHistory);
    }

    @Test
    @Transactional
    void putNonExistingAuditHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auditHistory.setId(longCount.incrementAndGet());

        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(auditHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auditHistory.setId(longCount.incrementAndGet());

        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(auditHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auditHistory.setId(longCount.incrementAndGet());

        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuditHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedAuditHistory = auditHistoryRepository.saveAndFlush(auditHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the auditHistory using partial update
        AuditHistory partialUpdatedAuditHistory = new AuditHistory();
        partialUpdatedAuditHistory.setId(auditHistory.getId());

        partialUpdatedAuditHistory
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .oldValue(UPDATED_OLD_VALUE)
            .usuario(UPDATED_USUARIO)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restAuditHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAuditHistory))
            )
            .andExpect(status().isOk());

        // Validate the AuditHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAuditHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAuditHistory, auditHistory),
            getPersistedAuditHistory(auditHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateAuditHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedAuditHistory = auditHistoryRepository.saveAndFlush(auditHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the auditHistory using partial update
        AuditHistory partialUpdatedAuditHistory = new AuditHistory();
        partialUpdatedAuditHistory.setId(auditHistory.getId());

        partialUpdatedAuditHistory
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .fieldName(UPDATED_FIELD_NAME)
            .oldValue(UPDATED_OLD_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .usuario(UPDATED_USUARIO)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restAuditHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAuditHistory))
            )
            .andExpect(status().isOk());

        // Validate the AuditHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAuditHistoryUpdatableFieldsEquals(partialUpdatedAuditHistory, getPersistedAuditHistory(partialUpdatedAuditHistory));
    }

    @Test
    @Transactional
    void patchNonExistingAuditHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auditHistory.setId(longCount.incrementAndGet());

        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(auditHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auditHistory.setId(longCount.incrementAndGet());

        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(auditHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auditHistory.setId(longCount.incrementAndGet());

        // Create the AuditHistory
        AuditHistoryDTO auditHistoryDTO = auditHistoryMapper.toDto(auditHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(auditHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuditHistory() throws Exception {
        // Initialize the database
        insertedAuditHistory = auditHistoryRepository.saveAndFlush(auditHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the auditHistory
        restAuditHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return auditHistoryRepository.count();
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

    protected AuditHistory getPersistedAuditHistory(AuditHistory auditHistory) {
        return auditHistoryRepository.findById(auditHistory.getId()).orElseThrow();
    }

    protected void assertPersistedAuditHistoryToMatchAllProperties(AuditHistory expectedAuditHistory) {
        assertAuditHistoryAllPropertiesEquals(expectedAuditHistory, getPersistedAuditHistory(expectedAuditHistory));
    }

    protected void assertPersistedAuditHistoryToMatchUpdatableProperties(AuditHistory expectedAuditHistory) {
        assertAuditHistoryAllUpdatablePropertiesEquals(expectedAuditHistory, getPersistedAuditHistory(expectedAuditHistory));
    }
}
