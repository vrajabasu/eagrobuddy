/*package com.eagro.security;

import com.eagro.entities.User;
import com.eagro.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

*//**
 * Authenticate a user from the database.
 *//*
@Component("userDetailsService")
public class LoginDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(LoginDetailsService.class);

	private final UserRepository userRepository;

	public LoginDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		Optional<User> userByEmailFromDatabase = userRepository.findOneWithUserRoleByEmail(lowercaseLogin);
		return userByEmailFromDatabase.map(user -> createSpringSecurityUser(lowercaseLogin, user)).orElseGet(() -> {
			Optional<User> userByLoginFromDatabase = userRepository.findOneWithuserRoleByLogin(lowercaseLogin);
			return userByLoginFromDatabase.map(user -> createSpringSecurityUser(lowercaseLogin, user))
					.orElseThrow(() -> new UsernameNotFoundException(
							"User " + lowercaseLogin + " was not found in the " + "database"));
		});
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActiveFlag()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().getRoleName()
        		));
        		
        return new org.springframework.security.core.userdetails.User(user.getLoginKey(),
            user.getPassword(),
            grantedAuthorities);
    }
}
*/