package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ClockInDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClockIn and its DTO ClockInDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityParticipationMapper.class})
public interface ClockInMapper extends EntityMapper<ClockInDTO, ClockIn> {

    @Mapping(source = "activityParticipation.id", target = "activityParticipationId")
    @Mapping(source = "pics", target = "pics")
    ClockInDTO toDto(ClockIn clockIn);

    @Mapping(target = "pics", ignore = true)
    @Mapping(source = "activityParticipationId", target = "activityParticipation")
    ClockIn toEntity(ClockInDTO clockInDTO);

    default ClockIn fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClockIn clockIn = new ClockIn();
        clockIn.setId(id);
        return clockIn;
    }
}
