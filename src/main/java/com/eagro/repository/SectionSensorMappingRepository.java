package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.SectionSensorMapping;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SectionSensorMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionSensorMappingRepository extends JpaRepository<SectionSensorMapping, Long> {

}
