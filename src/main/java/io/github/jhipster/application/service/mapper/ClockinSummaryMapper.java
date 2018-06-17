package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ClockinSummaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClockinSummary and its DTO ClockinSummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClockinSummaryMapper extends EntityMapper<ClockinSummaryDTO, ClockinSummary> {



    default ClockinSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClockinSummary clockinSummary = new ClockinSummary();
        clockinSummary.setId(id);
        return clockinSummary;
    }
}
