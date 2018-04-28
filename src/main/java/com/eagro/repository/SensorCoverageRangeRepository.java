package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.SensorCoverageRange;
import com.eagro.entities.SensorData;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the SensorCoverageRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorCoverageRangeRepository extends JpaRepository<SensorCoverageRange, Long> {
	@Query("select sensorCoverageRange from SensorCoverageRange sensorCoverageRange where sensorCoverageRange.layout.layoutId =:layoutId and sensorCoverageRange.section.sectionId =:sectionId and sensorCoverageRange.sensor.id =:sensorId")
	SensorCoverageRange findByLayoutIdAndSectionIdAndSensorId(@Param("layoutId") Long layoutId, @Param("sectionId") Long sectionId,
			@Param("sensorId") Long sensorId);
}
