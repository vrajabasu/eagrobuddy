package com.eagro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagro.entities.Section;

/**
 * Spring Data JPA repository for the Section entity.
 */
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

	@Query("select section from Section section where section.layout.layoutId =:layoutId")
	List<Section> findByLayoutId(@Param("layoutId") Long layoutId);

	@Query("select section from Section section where section.layout.layoutId =:layoutId and sectionId =:sectionId ")
	Section findByLayoutIdAndSectionId(@Param("layoutId") Long layoutId, @Param("sectionId") Long sectionId);

}
