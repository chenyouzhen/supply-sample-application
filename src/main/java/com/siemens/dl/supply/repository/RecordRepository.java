package com.siemens.dl.supply.repository;

import com.siemens.dl.supply.domain.Record;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Record entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
