package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.SensorData;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SensorData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

}
