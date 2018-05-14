package com.eagro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagro.entities.SensorData;


/**
 * Spring Data JPA repository for the SensorData entity.
 */

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

	@Query("select sensorData from SensorData sensorData where sensorData.layout.layoutId =:layoutId and sensorData.sensor_id =:sensor_id ORDER BY recordedDateTime ASC")
	SensorData findBySensorIdAndLayoutId(@Param("layoutId") Long layoutId,
			@Param("sensor_id") Long sensor_id);
	
	@Query("select sensorData from SensorData sensorData where sensorData.layout.layoutId =:layoutId and sensorData.sensor_id IN (:sensor_id) ORDER BY recordedDateTime ASC")
	List<SensorData> findByLayoutIdAndSensorIds(@Param("layoutId") Long layoutId,
			@Param("sensor_id") List<Long>  sensor_id);
	
}
