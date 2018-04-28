package com.eagro.repository;


import org.springframework.stereotype.Repository;

import com.eagro.entities.Layout;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Layout entity.
 */
@Repository
public interface LayoutRepository extends JpaRepository<Layout, Long> {
    @Query("select distinct layout from Layout layout left join fetch layout.userLayoutMappings")
    List<Layout> findAllWithEagerRelationships();

    @Query("select layout from Layout layout left join fetch layout.userLayoutMappings where layout.id =:id")
    Layout findOneWithEagerRelationships(@Param("id") Long id);

}
