package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.KPI;
import com.eagro.service.dto.KPIDTO;

/**
 * Mapper for the entity KPI and its DTO KPIDTO.
 */
@Mapper(componentModel = "spring", uses  = {LayoutMapper.class, SectionMapper.class})
public interface KPIMapper extends EntityMapper<KPIDTO, KPI> {

    @Mapping(source = "layout.layoutId", target = "layoutId")
    @Mapping(source = "section.sectionId", target = "sectionId")
    KPIDTO toDto(KPI kPI);

    @Mapping(source = "layoutId", target = "layout")
    @Mapping(source = "sectionId", target = "section")
    KPI toEntity(KPIDTO kPIDTO);

    default KPI fromId(Long id) {
        if (id == null) {
            return null;
        }
        KPI kPI = new KPI();
        kPI.setKpiId(id);
        return kPI;
    }
}
