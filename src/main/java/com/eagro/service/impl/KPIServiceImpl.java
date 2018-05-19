package com.eagro.service.impl;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.KPI;
import com.eagro.repository.KPIRepository;
import com.eagro.service.KPIService;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.mapper.KPIMapper;

/**
 * Service Implementation for managing KPI.
 */
@Service
@Transactional
public class KPIServiceImpl implements KPIService {

    private final Logger log = LoggerFactory.getLogger(KPIServiceImpl.class);

    @Autowired
    private KPIRepository kPIRepository;

    @Autowired
    private KPIMapper kPIMapper;

    /**
     * Save a kPI.
     *
     * @param kPIDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KPIDTO save(KPIDTO kPIDTO) {
        log.debug("Request to save KPI : {}", kPIDTO);
        KPI kPI = kPIMapper.toEntity(kPIDTO);
        kPI = kPIRepository.save(kPI);
        return kPIMapper.toDto(kPI);
    }

    /**
     * Get all the kPIS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KPIDTO> findAll() {
        log.debug("Request to get all KPIS");
        return kPIRepository.findAll().stream()
            .map(kPIMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one kPI by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KPIDTO findOne(Long id) {
        log.debug("Request to get KPI : {}", id);
        KPI kPI = kPIRepository.findOne(id);
        return kPIMapper.toDto(kPI);
    }

    /**
     * Delete the kPI by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KPI : {}", id);
        kPIRepository.delete(id);
    }
}
