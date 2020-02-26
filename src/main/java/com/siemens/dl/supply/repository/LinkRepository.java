package com.siemens.dl.supply.repository;

import com.siemens.dl.supply.domain.Link;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Link entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

}
