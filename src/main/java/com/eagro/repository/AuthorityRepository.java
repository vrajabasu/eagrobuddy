package com.eagro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagro.entities.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
