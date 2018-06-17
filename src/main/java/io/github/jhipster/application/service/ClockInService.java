package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ClockIn;
import io.github.jhipster.application.repository.ClockInRepository;
import io.github.jhipster.application.service.dto.ClockInDTO;
import io.github.jhipster.application.service.mapper.ClockInMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ClockIn.
 */
@Service
@Transactional
public class ClockInService {

    private final Logger log = LoggerFactory.getLogger(ClockInService.class);

    private final ClockInRepository clockInRepository;

    private final ClockInMapper clockInMapper;

    public ClockInService(ClockInRepository clockInRepository, ClockInMapper clockInMapper) {
        this.clockInRepository = clockInRepository;
        this.clockInMapper = clockInMapper;
    }

    /**
     * Save a clockIn.
     *
     * @param clockInDTO the entity to save
     * @return the persisted entity
     */
    public ClockInDTO save(ClockInDTO clockInDTO) {
        log.debug("Request to save ClockIn : {}", clockInDTO);
        ClockIn clockIn = clockInMapper.toEntity(clockInDTO);
        clockIn = clockInRepository.save(clockIn);
        return clockInMapper.toDto(clockIn);
    }

    /**
     * Get all the clockIns.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClockInDTO> findAll() {
        log.debug("Request to get all ClockIns");
        return clockInRepository.findAll().stream()
            .map(clockInMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one clockIn by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClockInDTO findOne(Long id) {
        log.debug("Request to get ClockIn : {}", id);
        ClockIn clockIn = clockInRepository.findOne(id);
        return clockInMapper.toDto(clockIn);
    }

    /**
     * Delete the clockIn by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClockIn : {}", id);
        clockInRepository.delete(id);
    }
}
