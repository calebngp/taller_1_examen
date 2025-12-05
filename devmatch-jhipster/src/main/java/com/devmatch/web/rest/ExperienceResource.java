package com.devmatch.web.rest;

import com.devmatch.repository.ExperienceRepository;
import com.devmatch.service.ExperienceService;
import com.devmatch.service.dto.ExperienceDTO;
import com.devmatch.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.devmatch.domain.Experience}.
 */
@RestController
@RequestMapping("/api/experiences")
public class ExperienceResource {

    private static final Logger LOG = LoggerFactory.getLogger(ExperienceResource.class);

    private static final String ENTITY_NAME = "experience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienceService experienceService;

    private final ExperienceRepository experienceRepository;

    public ExperienceResource(ExperienceService experienceService, ExperienceRepository experienceRepository) {
        this.experienceService = experienceService;
        this.experienceRepository = experienceRepository;
    }

    /**
     * {@code POST  /experiences} : Create a new experience.
     *
     * @param experienceDTO the experienceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new experienceDTO, or with status {@code 400 (Bad Request)} if the experience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ExperienceDTO> createExperience(@Valid @RequestBody ExperienceDTO experienceDTO) throws URISyntaxException {
        LOG.debug("REST request to save Experience : {}", experienceDTO);
        if (experienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new experience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        experienceDTO = experienceService.save(experienceDTO);
        return ResponseEntity.created(new URI("/api/experiences/" + experienceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, experienceDTO.getId().toString()))
            .body(experienceDTO);
    }

    /**
     * {@code PUT  /experiences/:id} : Updates an existing experience.
     *
     * @param id the id of the experienceDTO to save.
     * @param experienceDTO the experienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experienceDTO,
     * or with status {@code 400 (Bad Request)} if the experienceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the experienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDTO> updateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExperienceDTO experienceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Experience : {}, {}", id, experienceDTO);
        if (experienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experienceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        experienceDTO = experienceService.update(experienceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, experienceDTO.getId().toString()))
            .body(experienceDTO);
    }

    /**
     * {@code PATCH  /experiences/:id} : Partial updates given fields of an existing experience, field will ignore if it is null
     *
     * @param id the id of the experienceDTO to save.
     * @param experienceDTO the experienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experienceDTO,
     * or with status {@code 400 (Bad Request)} if the experienceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the experienceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the experienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExperienceDTO> partialUpdateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExperienceDTO experienceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Experience partially : {}, {}", id, experienceDTO);
        if (experienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experienceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExperienceDTO> result = experienceService.partialUpdate(experienceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, experienceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /experiences} : get all the experiences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of experiences in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ExperienceDTO>> getAllExperiences(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Experiences");
        Page<ExperienceDTO> page = experienceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /experiences/:id} : get the "id" experience.
     *
     * @param id the id of the experienceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the experienceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDTO> getExperience(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Experience : {}", id);
        Optional<ExperienceDTO> experienceDTO = experienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experienceDTO);
    }

    /**
     * {@code DELETE  /experiences/:id} : delete the "id" experience.
     *
     * @param id the id of the experienceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Experience : {}", id);
        experienceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
