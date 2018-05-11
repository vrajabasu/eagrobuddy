package com.eagro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagro.entities.SectionSensorMapping;
import com.eagro.entities.enumeration.ZoneType;

/**
 * Spring Data JPA repository for the SectionSensorMapping entity.
 */
@Repository
public interface SectionSensorMappingRepository extends JpaRepository<SectionSensorMapping, Long> {

	@Query("select sectionSensorMapping from SectionSensorMapping sectionSensorMapping where sectionSensorMapping.layout.layoutId =:layoutId and sectionSensorMapping.section.sectionId =:sectionId")
	List<SectionSensorMapping> findBySectionIdAndLayoutId(@Param("layoutId") Long layoutId,
			@Param("sectionId") Long sectionId);

	@Query("select sectionSensorMapping from SectionSensorMapping sectionSensorMapping where sectionSensorMapping.layout.layoutId =:layoutId and sectionSensorMapping.section.sectionId =:sectionId and zoneType =:zoneType")
	List<SectionSensorMapping> findByLayoutIdAndSectionIdAndZoneType(@Param("layoutId") Long layoutId,
			@Param("sectionId") Long sectionId, @Param("zoneType") ZoneType zoneType);
	
	
	@Query("select sectionSensorMapping from SectionSensorMapping sectionSensorMapping where sectionSensorMapping.layout.layoutId =:layoutId and sectionSensorMapping.section.sectionId =:sectionId and sectionSensorMapping.sensor_id =:sensor_id")
	SectionSensorMapping findByLayoutIdAndSectionIdAndSensorId(@Param("layoutId") Long layoutId,
			@Param("sectionId") Long sectionId, @Param("sensor_id") Long sensor_id);
}
