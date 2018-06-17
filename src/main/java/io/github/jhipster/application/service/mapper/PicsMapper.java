package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pics and its DTO PicsDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class, ClockInMapper.class})
public interface PicsMapper extends EntityMapper<PicsDTO, Pics> {

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "clockIn.id", target = "clockInId")
    PicsDTO toDto(Pics pics);

    @Mapping(source = "activityId", target = "activity")
    @Mapping(source = "clockInId", target = "clockIn")
    Pics toEntity(PicsDTO picsDTO);

    default Pics fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pics pics = new Pics();
        pics.setId(id);
        return pics;
    }
}
