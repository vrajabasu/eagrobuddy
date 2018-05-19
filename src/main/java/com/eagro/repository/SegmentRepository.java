package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.Section;
import com.eagro.entities.Segment;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Segment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
	@Query("select segment from Segment segment where segment.layout.layoutId =:layoutId and segment.section.sectionId =:sectionId")
    List<Segment> findByLayoutIdAndSectionId(@Param("layoutId") Long layoutId, @Param("sectionId") Long sectionId);
	
	@Query("select segment from Segment segment where segment.layout.layoutId =:layoutId and segment.section.sectionId =:sectionId and segment.segmentId =:segmentId")
	Segment findByLayoutIdAndSectionIdAndSegmentId(@Param("layoutId") Long layoutId, @Param("sectionId") Long sectionId, @Param("segmentId") Long segmentId);
	
}
