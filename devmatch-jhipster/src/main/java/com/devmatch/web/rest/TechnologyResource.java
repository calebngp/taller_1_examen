package com.devmatch.web.rest;

import com.devmatch.repository.TechnologyRepository;
import com.devmatch.service.TechnologyService;
import com.devmatch.service.dto.TechnologyDTO;
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
 * REST controller for managing {@link com.devmatch.domain.Technology}.
 */
@RestController
@RequestMapping("/api/technologies")
public class TechnologyResource {

    private static final Logger LOG = LoggerFactory.getLogger(TechnologyResource.class);

    private static final String ENTITY_NAME = "technology";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechnologyService technologyService;

    private final TechnologyRepository technologyRepository;

    public TechnologyResource(TechnologyService technologyService, TechnologyRepository technologyRepository) {
        this.technologyService = technologyService;
        this.technologyRepository = technologyRepository;
    }

    /**
     * {@code POST  /technologies} : Create a new technology.
     *
     * @param technologyDTO the technologyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new technologyDTO, or with status {@code 400 (Bad Request)} if the technology has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TechnologyDTO> createTechnology(@Valid @RequestBody TechnologyDTO technologyDTO) throws URISyntaxException {
        LOG.debug("REST request to save Technology : {}", technologyDTO);
        if (technologyDTO.getId() != null) {
            throw new BadRequestAlertException("A new technology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        technologyDTO = technologyService.save(technologyDTO);
        return ResponseEntity.created(new URI("/api/technologies/" + technologyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, technologyDTO.getId().toString()))
            .body(technologyDTO);
    }

    /**
     * {@code PUT  /technologies/:id} : Updates an existing technology.
     *
     * @param id the id of the technologyDTO to save.
     * @param technologyDTO the technologyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technologyDTO,
     * or with status {@code 400 (Bad Request)} if the technologyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the technologyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TechnologyDTO> updateTechnology(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TechnologyDTO technologyDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Technology : {}, {}", id, technologyDTO);
        if (technologyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, technologyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!technologyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        technologyDTO = technologyService.update(technologyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, technologyDTO.getId().toString()))
            .body(technologyDTO);
    }

    /**
     * {@code PATCH  /technologies/:id} : Partial updates given fields of an existing technology, field will ignore if it is null
     *
     * @param id the id of the technologyDTO to save.
     * @param technologyDTO the technologyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technologyDTO,
     * or with status {@code 400 (Bad Request)} if the technologyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the technologyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the technologyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TechnologyDTO> partialUpdateTechnology(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TechnologyDTO technologyDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Technology partially : {}, {}", id, technologyDTO);
        if (technologyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, technologyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!technologyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TechnologyDTO> result = technologyService.partialUpdate(technologyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, technologyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /technologies} : get all the technologies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of technologies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TechnologyDTO>> getAllTechnologies(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Technologies");
        Page<TechnologyDTO> page = technologyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /technologies/:id} : get the "id" technology.
     *
     * @param id the id of the technologyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the technologyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TechnologyDTO> getTechnology(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Technology : {}", id);
        Optional<TechnologyDTO> technologyDTO = technologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technologyDTO);
    }

    /**
     * {@code DELETE  /technologies/:id} : delete the "id" technology.
     *
     * @param id the id of the technologyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Technology : {}", id);
        technologyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
