package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Activity;
import io.github.jhipster.application.domain.ActivityParticipation;
import io.github.jhipster.application.repository.ActivityParticipationRepository;
import io.github.jhipster.application.repository.ActivityRepository;
import io.github.jhipster.application.service.dto.ActivityParticipationDTO;
import io.github.jhipster.application.service.mapper.ActivityParticipationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ActivityParticipation.
 */
@Service
@Transactional
public class ActivityParticipationService {

    private final Logger log = LoggerFactory.getLogger(ActivityParticipationService.class);

    private final ActivityParticipationRepository activityParticipationRepository;
    
    private final ActivityRepository activityRepository;

    private final ActivityParticipationMapper activityParticipationMapper;

    public ActivityParticipationService(ActivityParticipationRepository activityParticipationRepository,ActivityRepository activityRepository, ActivityParticipationMapper activityParticipationMapper) {
        this.activityParticipationRepository = activityParticipationRepository;
        this.activityParticipationMapper = activityParticipationMapper;
        this.activityRepository = activityRepository;
    }

    /**
     * Save a activityParticipation.
     *
     * @param activityParticipationDTO the entity to save
     * @return the persisted entity
     */
    public ActivityParticipationDTO save(ActivityParticipationDTO activityParticipationDTO) {
        log.debug("Request to save ActivityParticipation : {}", activityParticipationDTO);
        ActivityParticipation activityParticipation = activityParticipationMapper.toEntity(activityParticipationDTO);
        activityParticipation = activityParticipationRepository.save(activityParticipation);
        return activityParticipationMapper.toDto(activityParticipation);
    }

    /**
     * Get all the activityParticipations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ActivityParticipationDTO> findAll() {
        log.debug("Request to get all ActivityParticipations");
        return activityParticipationRepository.findAll().stream()
            .map(activityParticipationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one activityParticipation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ActivityParticipationDTO findOne(Long id) {
        log.debug("Request to get ActivityParticipation : {}", id);
        ActivityParticipation activityParticipation = activityParticipationRepository.findOne(id);
        return activityParticipationMapper.toDto(activityParticipation);
    }

    /**
     * Delete the activityParticipation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ActivityParticipation : {}", id);
        activityParticipationRepository.delete(id);
    }

	public List<ActivityParticipationDTO> findByActivity(Long activityId) {
		return activityParticipationRepository.findByActivityId(activityId).stream()
	            .map(activityParticipationMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
		
	}

	public List<ActivityParticipationDTO> findByWechatUserId(String wechatUserId) {
		return activityParticipationRepository.findByWechatUserId(wechatUserId).stream()
	            .map(activityParticipationMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
	}
}
