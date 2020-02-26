package com.siemens.dl.supply.service.mapper;


import com.siemens.dl.supply.domain.*;
import com.siemens.dl.supply.service.dto.LinkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Link} and its DTO {@link LinkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LinkMapper extends EntityMapper<LinkDTO, Link> {


    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "removeObservation", ignore = true)
    @Mapping(target = "assemblyline", ignore = true)
    Link toEntity(LinkDTO linkDTO);

    default Link fromId(Long id) {
        if (id == null) {
            return null;
        }
        Link link = new Link();
        link.setId(id);
        return link;
    }
}
