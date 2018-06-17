package io.github.jhipster.application.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.jhipster.application.domain.Activity;
import io.github.jhipster.application.service.dto.ActivityDTO;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {

	@Mapping(source = "activityParticipations", target = "activityParticipations")
	ActivityDTO toDto(Activity activity);
	
    @Mapping(target = "activityParticipations", ignore = true)
    @Mapping(target = "pics", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);

    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
