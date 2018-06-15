package com.eagro.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagro.entities.User;

/**
 * Spring Data JPA repository for the EagroUser entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";
	
	 Optional<User> findOneByActiveFlag(String activationKey);

	    List<User> findAllByActiveFlagIsFalseAndCreatedDateBefore(Instant dateTime);

	    //Optional<User> findOneByResetKey(String resetKey);

	    Optional<User> findOneByEmailAddressIgnoreCase(String email_address);

	    Optional<User> findOneByloginKey(String login);
	  
	    @EntityGraph(attributePaths = "authorities")
	    Optional<User> findOneWithAuthoritiesByuserId(Long user_id);

	    @EntityGraph(attributePaths = "authorities")
	    /*@Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)*/
	    Optional<User> findOneWithAuthoritiesByLoginKey(String login_key);

	    @EntityGraph(attributePaths = "authorities")
	    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
	    Optional<User> findOneWithAuthoritiesByEmailAddress(String email_address);
	    
	    Page<User> findAllByLoginKeyNot(Pageable pageable, String login_key);

	
	//Insertion
	

}
