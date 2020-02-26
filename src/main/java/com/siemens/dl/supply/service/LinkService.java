package com.siemens.dl.supply.service;

import com.siemens.dl.supply.service.dto.LinkDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.siemens.dl.supply.domain.Link}.
 */
public interface LinkService {

    /**
     * Save a link.
     *
     * @param linkDTO the entity to save.
     * @return the persisted entity.
     */
    LinkDTO save(LinkDTO linkDTO);

    /**
     * Get all the links.
     *
     * @return the list of entities.
     */
    List<LinkDTO> findAll();
    /**
     * Get all the LinkDTO where Assemblyline is {@code null}.
     *
     * @return the list of entities.
     */
    List<LinkDTO> findAllWhereAssemblylineIsNull();

    /**
     * Get the "id" link.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LinkDTO> findOne(Long id);

    /**
     * Delete the "id" link.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
