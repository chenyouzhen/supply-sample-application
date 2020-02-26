package com.siemens.dl.supply.service.mapper;


import com.siemens.dl.supply.domain.*;
import com.siemens.dl.supply.service.dto.FactoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Factory} and its DTO {@link FactoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FactoryMapper extends EntityMapper<FactoryDTO, Factory> {


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    @Mapping(target = "kpis", ignore = true)
    @Mapping(target = "removeKpi", ignore = true)
    @Mapping(target = "alarms", ignore = true)
    @Mapping(target = "removeAlarm", ignore = true)
    Factory toEntity(FactoryDTO factoryDTO);

    default Factory fromId(Long id) {
        if (id == null) {
            return null;
        }
        Factory factory = new Factory();
        factory.setId(id);
        return factory;
    }
}
