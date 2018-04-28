package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.SensorCoverageRange;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SensorCoverageRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorCoverageRangeRepository extends JpaRepository<SensorCoverageRange, Long> {

}
