package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.UserRole;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
