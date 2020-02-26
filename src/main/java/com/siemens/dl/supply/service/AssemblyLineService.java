package com.siemens.dl.supply.service;

import com.siemens.dl.supply.service.dto.AssemblyLineDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.siemens.dl.supply.domain.AssemblyLine}.
 */
public interface AssemblyLineService {

    /**
     * Save a assemblyLine.
     *
     * @param assemblyLineDTO the entity to save.
     * @return the persisted entity.
     */
    AssemblyLineDTO save(AssemblyLineDTO assemblyLineDTO);

    /**
     * Get all the assemblyLines.
     *
     * @return the list of entities.
     */
    List<AssemblyLineDTO> findAll();

    /**
     * Get the "id" assemblyLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssemblyLineDTO> findOne(Long id);

    /**
     * Delete the "id" assemblyLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
