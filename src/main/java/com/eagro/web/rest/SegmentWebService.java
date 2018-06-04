package com.eagro.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eagro.service.SegmentService;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing Segment.
 */
@RestController
@RequestMapping("/api")
public class SegmentWebService {

    private final Logger log = LoggerFactory.getLogger(SegmentWebService.class);

    private static final String ENTITY_NAME = "segment";

    @Autowired
    public SegmentService segmentService;

    /**
     * POST  /segments : Create a new segment.
     *
     * @param segmentDTO the segmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new segmentDTO, or with status 400 (Bad Request) if the segment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/segments",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SegmentDTO> createSegment(@RequestBody SegmentDTO segmentDTO) throws URISyntaxException {
        log.debug("REST request to save Segment : {}", segmentDTO);
        if (segmentDTO.getSegmentId() != null) {
            throw new BadRequestAlertException("A new segment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SegmentDTO result = segmentService.save(segmentDTO);
        return ResponseEntity.created(new URI("/api/segments/" + result.getSegmentId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getSegmentId().toString()))
            .body(result);
    }

    /**
     * PUT  /segments : Updates an existing segment.
     *
     * @param segmentDTO the segmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated segmentDTO,
     * or with status 400 (Bad Request) if the segmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the segmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/segments",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<SegmentDTO> updateSegment(@RequestBody SegmentDTO segmentDTO) throws URISyntaxException {
        log.debug("REST request to update Segment : {}", segmentDTO);
        if (segmentDTO.getSegmentId() == null) {
            return createSegment(segmentDTO);
        }
        SegmentDTO result = segmentService.save(segmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, segmentDTO.getSegmentId().toString()))
            .body(result);
    }

    /**
     * GET  /segments : get all the segments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of segments in body
     */
    @RequestMapping(value = "/segments",method = RequestMethod.GET)
    @Timed
    public List<SegmentDTO> getAllSegments() {
        log.debug("REST request to get all Segments");
        return segmentService.findAll();
        }

    /**
     * GET  /segments/:id : get the "id" segment.
     *
     * @param id the id of the segmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the segmentDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/segments/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<SegmentDTO> getSegment(@PathVariable Long id) {
        log.debug("REST request to get Segment : {}", id);
        SegmentDTO segmentDTO = segmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(segmentDTO));
    }

    /**
     * DELETE  /segments/:id : delete the "id" segment.
     *
     * @param id the id of the segmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/segments/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteSegment(@PathVariable Long id) {
        log.debug("REST request to delete Segment : {}", id);
        segmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
