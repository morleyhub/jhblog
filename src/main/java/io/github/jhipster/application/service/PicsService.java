package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Pics;
import io.github.jhipster.application.repository.PicsRepository;
import io.github.jhipster.application.service.dto.PicsDTO;
import io.github.jhipster.application.service.mapper.PicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pics.
 */
@Service
@Transactional
public class PicsService {

    private final Logger log = LoggerFactory.getLogger(PicsService.class);

    private final PicsRepository picsRepository;

    private final PicsMapper picsMapper;

    public PicsService(PicsRepository picsRepository, PicsMapper picsMapper) {
        this.picsRepository = picsRepository;
        this.picsMapper = picsMapper;
    }

    /**
     * Save a pics.
     *
     * @param picsDTO the entity to save
     * @return the persisted entity
     */
    public PicsDTO save(PicsDTO picsDTO) {
        log.debug("Request to save Pics : {}", picsDTO);
        Pics pics = picsMapper.toEntity(picsDTO);
        pics = picsRepository.save(pics);
        return picsMapper.toDto(pics);
    }

    /**
     * Get all the pics.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PicsDTO> findAll() {
        log.debug("Request to get all Pics");
        return picsRepository.findAll().stream()
            .map(picsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pics by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PicsDTO findOne(Long id) {
        log.debug("Request to get Pics : {}", id);
        Pics pics = picsRepository.findOne(id);
        return picsMapper.toDto(pics);
    }

    /**
     * Delete the pics by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pics : {}", id);
        picsRepository.delete(id);
    }
}
