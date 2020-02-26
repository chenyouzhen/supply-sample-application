package com.siemens.dl.supply.service.mapper;


import com.siemens.dl.supply.domain.*;
import com.siemens.dl.supply.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {FactoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "factory.id", target = "factoryId")
    @Mapping(source = "factory.name", target = "factoryName")
    ProductDTO toDto(Product product);

    @Mapping(target = "assemblylines", ignore = true)
    @Mapping(target = "removeAssemblyline", ignore = true)
    @Mapping(target = "kpis", ignore = true)
    @Mapping(target = "removeKpi", ignore = true)
    @Mapping(source = "factoryId", target = "factory")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
