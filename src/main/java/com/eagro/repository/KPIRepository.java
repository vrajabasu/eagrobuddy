package com.eagro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagro.entities.KPI;
import com.eagro.entities.enumeration.ZoneType;


/**
 * Spring Data JPA repository for the KPI entity.
 */
@Repository
public interface KPIRepository extends JpaRepository<KPI, Long> {

	@Query("select kpi from KPI kpi where kpi.layout.layoutId =:layoutId and kpi.section.sectionId =:sectionId and zoneType =:zoneType")
	List<KPI> findByLayoutIdAndSectionIdAndZoneType(@Param("layoutId") Long layoutId,
			@Param("sectionId") Long sectionId, @Param("zoneType") ZoneType zoneType);
}
