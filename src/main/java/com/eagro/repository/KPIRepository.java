package com.eagro.repository;


import org.springframework.stereotype.Repository;

import com.eagro.entities.KPI;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KPI entity.
 */
@Repository
public interface KPIRepository extends JpaRepository<KPI, Long> {

}
