package com.devmatch.web.rest;

import com.devmatch.repository.MatchResultRepository;
import com.devmatch.service.MatchResultService;
import com.devmatch.service.dto.MatchResultDTO;
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
 * REST controller for managing {@link com.devmatch.domain.MatchResult}.
 */
@RestController
@RequestMapping("/api/match-results")
public class MatchResultResource {

    private static final Logger LOG = LoggerFactory.getLogger(MatchResultResource.class);

    private static final String ENTITY_NAME = "matchResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchResultService matchResultService;

    private final MatchResultRepository matchResultRepository;

    public MatchResultResource(MatchResultService matchResultService, MatchResultRepository matchResultRepository) {
        this.matchResultService = matchResultService;
        this.matchResultRepository = matchResultRepository;
    }

    /**
     * {@code POST  /match-results} : Create a new matchResult.
     *
     * @param matchResultDTO the matchResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchResultDTO, or with status {@code 400 (Bad Request)} if the matchResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchResultDTO> createMatchResult(@Valid @RequestBody MatchResultDTO matchResultDTO) throws URISyntaxException {
        LOG.debug("REST request to save MatchResult : {}", matchResultDTO);
        if (matchResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchResultDTO = matchResultService.save(matchResultDTO);
        return ResponseEntity.created(new URI("/api/match-results/" + matchResultDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, matchResultDTO.getId().toString()))
            .body(matchResultDTO);
    }

    /**
     * {@code PUT  /match-results/:id} : Updates an existing matchResult.
     *
     * @param id the id of the matchResultDTO to save.
     * @param matchResultDTO the matchResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchResultDTO,
     * or with status {@code 400 (Bad Request)} if the matchResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchResultDTO> updateMatchResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MatchResultDTO matchResultDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MatchResult : {}, {}", id, matchResultDTO);
        if (matchResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchResultDTO = matchResultService.update(matchResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matchResultDTO.getId().toString()))
            .body(matchResultDTO);
    }

    /**
     * {@code PATCH  /match-results/:id} : Partial updates given fields of an existing matchResult, field will ignore if it is null
     *
     * @param id the id of the matchResultDTO to save.
     * @param matchResultDTO the matchResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchResultDTO,
     * or with status {@code 400 (Bad Request)} if the matchResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchResultDTO> partialUpdateMatchResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MatchResultDTO matchResultDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MatchResult partially : {}, {}", id, matchResultDTO);
        if (matchResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchResultDTO> result = matchResultService.partialUpdate(matchResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matchResultDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /match-results} : get all the matchResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchResults in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MatchResultDTO>> getAllMatchResults(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MatchResults");
        Page<MatchResultDTO> page = matchResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-results/:id} : get the "id" matchResult.
     *
     * @param id the id of the matchResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchResultDTO> getMatchResult(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MatchResult : {}", id);
        Optional<MatchResultDTO> matchResultDTO = matchResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchResultDTO);
    }

    /**
     * {@code DELETE  /match-results/:id} : delete the "id" matchResult.
     *
     * @param id the id of the matchResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchResult(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MatchResult : {}", id);
        matchResultService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
