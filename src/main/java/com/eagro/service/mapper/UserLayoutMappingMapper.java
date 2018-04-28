package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.UserLayoutMapping;
import com.eagro.service.dto.UserLayoutMappingDTO;

/**
 * Mapper for the entity UserLayoutMapping and its DTO UserLayoutMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserLayoutMappingMapper extends EntityMapper<UserLayoutMappingDTO, UserLayoutMapping> {


    @Mapping(target = "layouts", ignore = true)
    @Mapping(target = "eagroUsers", ignore = true)
    UserLayoutMapping toEntity(UserLayoutMappingDTO userLayoutMappingDTO);

    default UserLayoutMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserLayoutMapping userLayoutMapping = new UserLayoutMapping();
        userLayoutMapping.setId(id);
        return userLayoutMapping;
    }
}
