package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.User;
import com.eagro.service.dto.UserDTO;

/**
 * Mapper for the entity EagroUser and its DTO EagroUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserLayoutMappingMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {


    @Mapping(target = "userRole", ignore = true)
    User toEntity(UserDTO eagroUserDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User eagroUser = new User();
        eagroUser.setUserId(id);
        return eagroUser;
    }
}
