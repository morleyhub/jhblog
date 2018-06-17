package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ActivityParticipationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActivityParticipation and its DTO ActivityParticipationDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class})
public interface ActivityParticipationMapper extends EntityMapper<ActivityParticipationDTO, ActivityParticipation> {

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "clockIns", target = "clockIns")
    //@Mapping(source = "clockIns.pics", target = "clockIns.pics")
    ActivityParticipationDTO toDto(ActivityParticipation activityParticipation);

    @Mapping(target = "clockIns", ignore = true)
    @Mapping(source = "activityId", target = "activity")
    ActivityParticipation toEntity(ActivityParticipationDTO activityParticipationDTO);

    default ActivityParticipation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityParticipation activityParticipation = new ActivityParticipation();
        activityParticipation.setId(id);
        return activityParticipation;
    }
}
