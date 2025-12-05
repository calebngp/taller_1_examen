package com.devmatch.web.rest;

import com.devmatch.repository.DeveloperRepository;
import com.devmatch.service.DeveloperService;
import com.devmatch.service.dto.DeveloperDTO;
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
 * REST controller for managing {@link com.devmatch.domain.Developer}.
 */
@RestController
@RequestMapping("/api/developers")
public class DeveloperResource {

    private static final Logger LOG = LoggerFactory.getLogger(DeveloperResource.class);

    private static final String ENTITY_NAME = "developer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeveloperService developerService;

    private final DeveloperRepository developerRepository;

    public DeveloperResource(DeveloperService developerService, DeveloperRepository developerRepository) {
        this.developerService = developerService;
        this.developerRepository = developerRepository;
    }

    /**
     * {@code POST  /developers} : Create a new developer.
     *
     * @param developerDTO the developerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new developerDTO, or with status {@code 400 (Bad Request)} if the developer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DeveloperDTO> createDeveloper(@Valid @RequestBody DeveloperDTO developerDTO) throws URISyntaxException {
        LOG.debug("REST request to save Developer : {}", developerDTO);
        if (developerDTO.getId() != null) {
            throw new BadRequestAlertException("A new developer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        developerDTO = developerService.save(developerDTO);
        return ResponseEntity.created(new URI("/api/developers/" + developerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, developerDTO.getId().toString()))
            .body(developerDTO);
    }

    /**
     * {@code PUT  /developers/:id} : Updates an existing developer.
     *
     * @param id the id of the developerDTO to save.
     * @param developerDTO the developerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developerDTO,
     * or with status {@code 400 (Bad Request)} if the developerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the developerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDTO> updateDeveloper(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DeveloperDTO developerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Developer : {}, {}", id, developerDTO);
        if (developerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, developerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!developerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        developerDTO = developerService.update(developerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developerDTO.getId().toString()))
            .body(developerDTO);
    }

    /**
     * {@code PATCH  /developers/:id} : Partial updates given fields of an existing developer, field will ignore if it is null
     *
     * @param id the id of the developerDTO to save.
     * @param developerDTO the developerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developerDTO,
     * or with status {@code 400 (Bad Request)} if the developerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the developerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the developerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeveloperDTO> partialUpdateDeveloper(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DeveloperDTO developerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Developer partially : {}, {}", id, developerDTO);
        if (developerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, developerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!developerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeveloperDTO> result = developerService.partialUpdate(developerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /developers} : get all the developers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of developers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DeveloperDTO>> getAllDevelopers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Developers");
        Page<DeveloperDTO> page;
        if (eagerload) {
            page = developerService.findAllWithEagerRelationships(pageable);
        } else {
            page = developerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /developers/:id} : get the "id" developer.
     *
     * @param id the id of the developerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the developerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDTO> getDeveloper(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Developer : {}", id);
        Optional<DeveloperDTO> developerDTO = developerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(developerDTO);
    }

    /**
     * {@code DELETE  /developers/:id} : delete the "id" developer.
     *
     * @param id the id of the developerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Developer : {}", id);
        developerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
