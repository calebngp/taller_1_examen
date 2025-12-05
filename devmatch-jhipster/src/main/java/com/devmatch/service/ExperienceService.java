package com.devmatch.service;

import com.devmatch.domain.Experience;
import com.devmatch.repository.ExperienceRepository;
import com.devmatch.service.dto.ExperienceDTO;
import com.devmatch.service.mapper.ExperienceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.devmatch.domain.Experience}.
 */
@Service
@Transactional
public class ExperienceService {

    private static final Logger LOG = LoggerFactory.getLogger(ExperienceService.class);

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    public ExperienceService(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    /**
     * Save a experience.
     *
     * @param experienceDTO the entity to save.
     * @return the persisted entity.
     */
    public ExperienceDTO save(ExperienceDTO experienceDTO) {
        LOG.debug("Request to save Experience : {}", experienceDTO);
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    /**
     * Update a experience.
     *
     * @param experienceDTO the entity to save.
     * @return the persisted entity.
     */
    public ExperienceDTO update(ExperienceDTO experienceDTO) {
        LOG.debug("Request to update Experience : {}", experienceDTO);
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    /**
     * Partially update a experience.
     *
     * @param experienceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExperienceDTO> partialUpdate(ExperienceDTO experienceDTO) {
        LOG.debug("Request to partially update Experience : {}", experienceDTO);

        return experienceRepository
            .findById(experienceDTO.getId())
            .map(existingExperience -> {
                experienceMapper.partialUpdate(existingExperience, experienceDTO);

                return existingExperience;
            })
            .map(experienceRepository::save)
            .map(experienceMapper::toDto);
    }

    /**
     * Get all the experiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExperienceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Experiences");
        return experienceRepository.findAll(pageable).map(experienceMapper::toDto);
    }

    /**
     * Get one experience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExperienceDTO> findOne(Long id) {
        LOG.debug("Request to get Experience : {}", id);
        return experienceRepository.findById(id).map(experienceMapper::toDto);
    }

    /**
     * Delete the experience by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Experience : {}", id);
        experienceRepository.deleteById(id);
    }
}
