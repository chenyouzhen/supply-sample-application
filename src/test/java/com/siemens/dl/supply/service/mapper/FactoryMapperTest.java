package com.siemens.dl.supply.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FactoryMapperTest {

    private FactoryMapper factoryMapper;

    @BeforeEach
    public void setUp() {
        factoryMapper = new FactoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(factoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(factoryMapper.fromId(null)).isNull();
    }
}
