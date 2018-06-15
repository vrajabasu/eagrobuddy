package com.eagro.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eagro.entities.Authority;
import com.eagro.entities.User;
import com.eagro.service.dto.UserDTO;

/**
 * Mapper for the entity EagroUser and its DTO EagroUserDTO.
 */
@Service
public class UserMapper {

	public UserDTO userToUserDTO(User user) {
		return new UserDTO(user);
	}

	public List<UserDTO> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
	}

	public User userDTOToUser(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		} else {
			User user = new User();
			user.setUserId(userDTO.getUserId());
			user.setLoginKey(userDTO.getLoginKey());
			user.setFirstName(userDTO.getFirstName());
			user.setMiddleName(userDTO.getMiddleName());
			user.setLastName(userDTO.getLastName());
			user.setEmailAddress(userDTO.getEmailAddress());
			user.setActiveFlag(userDTO.getActiveFlag());
			user.setUpdatedBy(userDTO.getUpdatedBy());
			user.setUpdatedDate(userDTO.getUpdatedDate());
			user.setCreatedBy(userDTO.getCreatedBy());
			user.setCreatedDate(userDTO.getCreatedDate());
			Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
			if (authorities != null) {
				user.setAuthorities(authorities);
			}
			return user;
		}
	}

	public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
		return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
	}

	public User userFromId(Long id) {
		if (id == null) {
			return null;
		}
		User user = new User();
		user.setUserId(id);
		return user;
	}

	public Set<Authority> authoritiesFromStrings(Set<String> strings) {
		return strings.stream().map(string -> {
			Authority auth = new Authority();
			auth.setName(string);
			return auth;
		}).collect(Collectors.toSet());
	}
}