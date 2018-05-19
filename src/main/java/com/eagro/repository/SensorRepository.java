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

	@Query("select s from Sensor s where s.layout.layoutId =:layoutId and sensor_id =:sensor_id")
	Sensor findByLayoutIdAndSensorId(@Param("layoutId") Long layoutId, @Param("sensor_id") Long sensor_id);
}
