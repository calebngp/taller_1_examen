package com.devmatch.service;

import com.devmatch.domain.Developer;
import com.devmatch.repository.DeveloperRepository;
import com.devmatch.service.dto.DeveloperDTO;
import com.devmatch.service.mapper.DeveloperMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.devmatch.domain.Developer}.
 */
@Service
@Transactional
public class DeveloperService {

    private static final Logger LOG = LoggerFactory.getLogger(DeveloperService.class);

    private final DeveloperRepository developerRepository;

    private final DeveloperMapper developerMapper;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperMapper developerMapper) {
        this.developerRepository = developerRepository;
        this.developerMapper = developerMapper;
    }

    /**
     * Save a developer.
     *
     * @param developerDTO the entity to save.
     * @return the persisted entity.
     */
    public DeveloperDTO save(DeveloperDTO developerDTO) {
        LOG.debug("Request to save Developer : {}", developerDTO);
        Developer developer = developerMapper.toEntity(developerDTO);
        developer = developerRepository.save(developer);
        return developerMapper.toDto(developer);
    }

    /**
     * Update a developer.
     *
     * @param developerDTO the entity to save.
     * @return the persisted entity.
     */
    public DeveloperDTO update(DeveloperDTO developerDTO) {
        LOG.debug("Request to update Developer : {}", developerDTO);
        Developer developer = developerMapper.toEntity(developerDTO);
        developer = developerRepository.save(developer);
        return developerMapper.toDto(developer);
    }

    /**
     * Partially update a developer.
     *
     * @param developerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeveloperDTO> partialUpdate(DeveloperDTO developerDTO) {
        LOG.debug("Request to partially update Developer : {}", developerDTO);

        return developerRepository
            .findById(developerDTO.getId())
            .map(existingDeveloper -> {
                developerMapper.partialUpdate(existingDeveloper, developerDTO);

                return existingDeveloper;
            })
            .map(developerRepository::save)
            .map(developerMapper::toDto);
    }

    /**
     * Get all the developers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeveloperDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Developers");
        return developerRepository.findAll(pageable).map(developerMapper::toDto);
    }

    /**
     * Get all the developers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DeveloperDTO> findAllWithEagerRelationships(Pageable pageable) {
        return developerRepository.findAllWithEagerRelationships(pageable).map(developerMapper::toDto);
    }

    /**
     * Get one developer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeveloperDTO> findOne(Long id) {
        LOG.debug("Request to get Developer : {}", id);
        return developerRepository.findOneWithEagerRelationships(id).map(developerMapper::toDto);
    }

    /**
     * Delete the developer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Developer : {}", id);
        developerRepository.deleteById(id);
    }
}
