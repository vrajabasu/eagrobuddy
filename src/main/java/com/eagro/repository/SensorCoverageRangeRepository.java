package com.eagro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagro.entities.SensorCoverageRange;


/**
 * Spring Data JPA repository for the SensorCoverageRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorCoverageRangeRepository extends JpaRepository<SensorCoverageRange, Long> {
	@Query("select sensorCoverageRange from SensorCoverageRange sensorCoverageRange where sensorCoverageRange.layout.layoutId =:layoutId and sensorCoverageRange.section.sectionId =:sectionId and sensorCoverageRange.sensor_id IN (:sensor_id)")
	List<SensorCoverageRange> findByLayoutIdAndSectionIdAndSensorId(@Param("layoutId") Long layoutId, @Param("sectionId") Long sectionId,
			@Param("sensor_id") List<Long> sensor_id);
}
