package com.devmatch.service;

import com.devmatch.domain.Technology;
import com.devmatch.repository.TechnologyRepository;
import com.devmatch.service.dto.TechnologyDTO;
import com.devmatch.service.mapper.TechnologyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.devmatch.domain.Technology}.
 */
@Service
@Transactional
public class TechnologyService {

    private static final Logger LOG = LoggerFactory.getLogger(TechnologyService.class);

    private final TechnologyRepository technologyRepository;

    private final TechnologyMapper technologyMapper;

    public TechnologyService(TechnologyRepository technologyRepository, TechnologyMapper technologyMapper) {
        this.technologyRepository = technologyRepository;
        this.technologyMapper = technologyMapper;
    }

    /**
     * Save a technology.
     *
     * @param technologyDTO the entity to save.
     * @return the persisted entity.
     */
    public TechnologyDTO save(TechnologyDTO technologyDTO) {
        LOG.debug("Request to save Technology : {}", technologyDTO);
        Technology technology = technologyMapper.toEntity(technologyDTO);
        technology = technologyRepository.save(technology);
        return technologyMapper.toDto(technology);
    }

    /**
     * Update a technology.
     *
     * @param technologyDTO the entity to save.
     * @return the persisted entity.
     */
    public TechnologyDTO update(TechnologyDTO technologyDTO) {
        LOG.debug("Request to update Technology : {}", technologyDTO);
        Technology technology = technologyMapper.toEntity(technologyDTO);
        technology = technologyRepository.save(technology);
        return technologyMapper.toDto(technology);
    }

    /**
     * Partially update a technology.
     *
     * @param technologyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TechnologyDTO> partialUpdate(TechnologyDTO technologyDTO) {
        LOG.debug("Request to partially update Technology : {}", technologyDTO);

        return technologyRepository
            .findById(technologyDTO.getId())
            .map(existingTechnology -> {
                technologyMapper.partialUpdate(existingTechnology, technologyDTO);

                return existingTechnology;
            })
            .map(technologyRepository::save)
            .map(technologyMapper::toDto);
    }

    /**
     * Get all the technologies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TechnologyDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Technologies");
        return technologyRepository.findAll(pageable).map(technologyMapper::toDto);
    }

    /**
     * Get one technology by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechnologyDTO> findOne(Long id) {
        LOG.debug("Request to get Technology : {}", id);
        return technologyRepository.findById(id).map(technologyMapper::toDto);
    }

    /**
     * Delete the technology by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Technology : {}", id);
        technologyRepository.deleteById(id);
    }
}
