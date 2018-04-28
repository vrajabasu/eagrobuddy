package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.UserLayoutMapping;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserLayoutMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserLayoutMappingRepository extends JpaRepository<UserLayoutMapping, Long> {

}
