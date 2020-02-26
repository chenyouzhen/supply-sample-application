package com.siemens.dl.supply.service.mapper;


import com.siemens.dl.supply.domain.*;
import com.siemens.dl.supply.service.dto.ObservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Observation} and its DTO {@link ObservationDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssemblyLineMapper.class, LinkMapper.class})
public interface ObservationMapper extends EntityMapper<ObservationDTO, Observation> {

    @Mapping(source = "assemblyline.id", target = "assemblylineId")
    @Mapping(source = "assemblyline.name", target = "assemblylineName")
    @Mapping(source = "link.id", target = "linkId")
    @Mapping(source = "link.name", target = "linkName")
    ObservationDTO toDto(Observation observation);

    @Mapping(source = "assemblylineId", target = "assemblyline")
    @Mapping(source = "linkId", target = "link")
    Observation toEntity(ObservationDTO observationDTO);

    default Observation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Observation observation = new Observation();
        observation.setId(id);
        return observation;
    }
}
