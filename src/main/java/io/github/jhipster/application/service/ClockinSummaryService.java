package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ClockinSummary;
import io.github.jhipster.application.repository.ClockinSummaryRepository;
import io.github.jhipster.application.service.dto.ClockinSummaryDTO;
import io.github.jhipster.application.service.mapper.ClockinSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ClockinSummary.
 */
@Service
@Transactional
public class ClockinSummaryService {

    private final Logger log = LoggerFactory.getLogger(ClockinSummaryService.class);

    private final ClockinSummaryRepository clockinSummaryRepository;

    private final ClockinSummaryMapper clockinSummaryMapper;

    public ClockinSummaryService(ClockinSummaryRepository clockinSummaryRepository, ClockinSummaryMapper clockinSummaryMapper) {
        this.clockinSummaryRepository = clockinSummaryRepository;
        this.clockinSummaryMapper = clockinSummaryMapper;
    }

    /**
     * Save a clockinSummary.
     *
     * @param clockinSummaryDTO the entity to save
     * @return the persisted entity
     */
    public ClockinSummaryDTO save(ClockinSummaryDTO clockinSummaryDTO) {
        log.debug("Request to save ClockinSummary : {}", clockinSummaryDTO);
        ClockinSummary clockinSummary = clockinSummaryMapper.toEntity(clockinSummaryDTO);
        clockinSummary = clockinSummaryRepository.save(clockinSummary);
        return clockinSummaryMapper.toDto(clockinSummary);
    }

    /**
     * Get all the clockinSummaries.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClockinSummaryDTO> findAll() {
        log.debug("Request to get all ClockinSummaries");
        return clockinSummaryRepository.findAll().stream()
            .map(clockinSummaryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one clockinSummary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClockinSummaryDTO findOne(Long id) {
        log.debug("Request to get ClockinSummary : {}", id);
        ClockinSummary clockinSummary = clockinSummaryRepository.findOne(id);
        return clockinSummaryMapper.toDto(clockinSummary);
    }

    /**
     * Delete the clockinSummary by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClockinSummary : {}", id);
        clockinSummaryRepository.delete(id);
    }

	public List<ClockinSummaryDTO> findByWechatUserId(String wechatUserId) {
		return clockinSummaryRepository.findByWechatUserId(wechatUserId).stream()
	            .map(clockinSummaryMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
	}
}
