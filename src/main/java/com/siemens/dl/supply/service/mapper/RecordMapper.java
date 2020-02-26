package com.siemens.dl.supply.service.mapper;


import com.siemens.dl.supply.domain.*;
import com.siemens.dl.supply.service.dto.RecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Record} and its DTO {@link RecordDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssemblyLineMapper.class})
public interface RecordMapper extends EntityMapper<RecordDTO, Record> {

    @Mapping(source = "assemblyline.id", target = "assemblylineId")
    @Mapping(source = "assemblyline.name", target = "assemblylineName")
    RecordDTO toDto(Record record);

    @Mapping(source = "assemblylineId", target = "assemblyline")
    Record toEntity(RecordDTO recordDTO);

    default Record fromId(Long id) {
        if (id == null) {
            return null;
        }
        Record record = new Record();
        record.setId(id);
        return record;
    }
}
