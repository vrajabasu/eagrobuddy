package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.UserRole;
import com.eagro.service.dto.UserRoleDTO;

/**
 * Mapper for the entity UserRole and its DTO UserRoleDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {

    UserRoleDTO toDto(UserRole userRole);

    UserRole toEntity(UserRoleDTO userRoleDTO);

    default UserRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        userRole.setRoleId(id);
        return userRole;
    }
}
