package com.eagro.repository;

import org.springframework.stereotype.Repository;

import com.eagro.entities.User;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the EagroUser entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
/*	String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";
	
	//Login related Codes
	
	 Optional<User> findOneByActivationKey(String activationKey);

	    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

	    Optional<User> findOneByResetKey(String resetKey);

	    Optional<User> findOneByEmailIgnoreCase(String email);

	    Optional<User> findOneByLogin(String login);
	    
	    *//** Not Used **//*
	    @EntityGraph(value = "userRole")
	    Optional<User> findOneWithUserRoleById(Long id);

	    @EntityGraph(value = "userRole")
	    @Cacheable(value = USERS_BY_LOGIN_CACHE)
	    Optional<User> findOneWithuserRoleByLogin(String login);
	    
	    
	    @EntityGraph(value = "userRole")
	    @Cacheable(value = USERS_BY_EMAIL_CACHE)
	    Optional<User> findOneWithUserRoleByEmail(String email);
	    
	    Page<User> findAllByLoginNot(Pageable pageable, String login);*/

	
	//Insertion
	
    @Query("select distinct user from User user left join fetch user.userLayoutMappings")
    List<User> findAllWithEagerRelationships();

    @Query("select user from User user left join fetch user.userLayoutMappings where user.id =:id")
    User findOneWithEagerRelationships(@Param("id") Long id);

}
