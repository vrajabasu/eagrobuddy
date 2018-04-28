package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.Sensor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Sensor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    @Query("select distinct sensor from Sensor sensor left join fetch sensor.sensorCoverageRanges left join fetch sensor.sectionSensorMappings left join fetch sensor.sensorData")
    List<Sensor> findAllWithEagerRelationships();

    @Query("select sensor from Sensor sensor left join fetch sensor.sensorCoverageRanges left join fetch sensor.sectionSensorMappings left join fetch sensor.sensorData where sensor.id =:id")
    Sensor findOneWithEagerRelationships(@Param("id") Long id);

}
