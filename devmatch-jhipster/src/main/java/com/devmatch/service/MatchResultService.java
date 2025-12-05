package com.devmatch.service;

import com.devmatch.domain.MatchResult;
import com.devmatch.repository.MatchResultRepository;
import com.devmatch.service.dto.MatchResultDTO;
import com.devmatch.service.mapper.MatchResultMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.devmatch.domain.MatchResult}.
 */
@Service
@Transactional
public class MatchResultService {

    private static final Logger LOG = LoggerFactory.getLogger(MatchResultService.class);

    private final MatchResultRepository matchResultRepository;

    private final MatchResultMapper matchResultMapper;

    public MatchResultService(MatchResultRepository matchResultRepository, MatchResultMapper matchResultMapper) {
        this.matchResultRepository = matchResultRepository;
        this.matchResultMapper = matchResultMapper;
    }

    /**
     * Save a matchResult.
     *
     * @param matchResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchResultDTO save(MatchResultDTO matchResultDTO) {
        LOG.debug("Request to save MatchResult : {}", matchResultDTO);
        MatchResult matchResult = matchResultMapper.toEntity(matchResultDTO);
        matchResult = matchResultRepository.save(matchResult);
        return matchResultMapper.toDto(matchResult);
    }

    /**
     * Update a matchResult.
     *
     * @param matchResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchResultDTO update(MatchResultDTO matchResultDTO) {
        LOG.debug("Request to update MatchResult : {}", matchResultDTO);
        MatchResult matchResult = matchResultMapper.toEntity(matchResultDTO);
        matchResult = matchResultRepository.save(matchResult);
        return matchResultMapper.toDto(matchResult);
    }

    /**
     * Partially update a matchResult.
     *
     * @param matchResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MatchResultDTO> partialUpdate(MatchResultDTO matchResultDTO) {
        LOG.debug("Request to partially update MatchResult : {}", matchResultDTO);

        return matchResultRepository
            .findById(matchResultDTO.getId())
            .map(existingMatchResult -> {
                matchResultMapper.partialUpdate(existingMatchResult, matchResultDTO);

                return existingMatchResult;
            })
            .map(matchResultRepository::save)
            .map(matchResultMapper::toDto);
    }

    /**
     * Get all the matchResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchResultDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MatchResults");
        return matchResultRepository.findAll(pageable).map(matchResultMapper::toDto);
    }

    /**
     * Get one matchResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MatchResultDTO> findOne(Long id) {
        LOG.debug("Request to get MatchResult : {}", id);
        return matchResultRepository.findById(id).map(matchResultMapper::toDto);
    }

    /**
     * Delete the matchResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MatchResult : {}", id);
        matchResultRepository.deleteById(id);
    }
}
