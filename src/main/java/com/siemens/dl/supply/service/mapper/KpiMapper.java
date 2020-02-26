package com.siemens.dl.supply.service.mapper;


import com.siemens.dl.supply.domain.*;
import com.siemens.dl.supply.service.dto.KpiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Kpi} and its DTO {@link KpiDTO}.
 */
@Mapper(componentModel = "spring", uses = {FactoryMapper.class, ProductMapper.class})
public interface KpiMapper extends EntityMapper<KpiDTO, Kpi> {

    @Mapping(source = "factory.id", target = "factoryId")
    @Mapping(source = "factory.name", target = "factoryName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    KpiDTO toDto(Kpi kpi);

    @Mapping(source = "factoryId", target = "factory")
    @Mapping(source = "productId", target = "product")
    Kpi toEntity(KpiDTO kpiDTO);

    default Kpi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kpi kpi = new Kpi();
        kpi.setId(id);
        return kpi;
    }
}
