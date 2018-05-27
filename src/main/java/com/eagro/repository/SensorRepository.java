package com.eagro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagro.entities.Sensor;

/**
 * Spring Data JPA repository for the Sensor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

	@Query("select s from Sensor s where s.layout.layoutId =:layoutId and sensor_id IN (:sensor_id)")
	List<Sensor> findByLayoutIdAndSensorId(@Param("layoutId") Long layoutId, @Param("sensor_id") List<Long> sensor_id);
}
