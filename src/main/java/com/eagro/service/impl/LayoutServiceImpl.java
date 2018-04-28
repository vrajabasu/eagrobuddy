package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.Layout;
import com.eagro.repository.LayoutRepository;
import com.eagro.service.LayoutService;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.mapper.LayoutMapper;

/**
 * Service Implementation for managing Layout.
 */
@Service
@Transactional
public class LayoutServiceImpl implements LayoutService {

    private final Logger log = LoggerFactory.getLogger(LayoutServiceImpl.class);

    @Autowired
    public LayoutRepository layoutRepository;

    @Autowired
    public LayoutMapper layoutMapper;

     /* Save a layout.
     *
     * @param layoutDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LayoutDTO save(LayoutDTO layoutDTO) {
        log.debug("Request to save Layout : {}", layoutDTO);
        Layout layout = layoutMapper.toEntity(layoutDTO);
        layout = layoutRepository.save(layout);
        return layoutMapper.toDto(layout);
    }

    /**
     * Get all the layouts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LayoutDTO> findAll() {
        log.debug("Request to get all Layouts");
        return layoutRepository.findAll().stream()
            .map(layoutMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one layout by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LayoutDTO findOne(Long id) {
        log.debug("Request to get Layout : {}", id);
        Layout layout = layoutRepository.findOne(id);
        return layoutMapper.toDto(layout);
    }

    /**
     * Delete the layout by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Layout : {}", id);
        layoutRepository.delete(id);
    }
}
