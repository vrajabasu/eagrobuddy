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
import com.eagro.service.KPIService;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing KPI.
 */
@RestController
@RequestMapping("/api")
public class KPIResource {

	private final Logger log = LoggerFactory.getLogger(KPIResource.class);

	private static final String ENTITY_NAME = "kPI";

	@Autowired
	public KPIService kPIService;


	/**
	 * POST /kpis : Create a new kPI.
	 *
	 * @param kPIDTO
	 *            the kPIDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new kPIDTO, or with status 400 (Bad Request) if the kPI has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/kpis",method = RequestMethod.POST)
	@Timed
	public ResponseEntity<KPIDTO> createKPI(@RequestBody KPIDTO kPIDTO) throws URISyntaxException {
		log.debug("REST request to save KPI : {}", kPIDTO);
		if (kPIDTO.getKpiId() != null) {
			throw new BadRequestAlertException("A new kPI cannot already have an ID", ENTITY_NAME, "idexists");
		}
		KPIDTO result = kPIService.save(kPIDTO);
		return ResponseEntity.created(new URI("/api/kpis/" + result.getKpiId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getKpiId().toString())).body(result);
	}

	/**
	 * PUT /kpis : Updates an existing kPI.
	 *
	 * @param kPIDTO
	 *            the kPIDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         kPIDTO, or with status 400 (Bad Request) if the kPIDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the kPIDTO
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/kpis",method = RequestMethod.PUT)
	@Timed
	public ResponseEntity<KPIDTO> updateKPI(@RequestBody KPIDTO kPIDTO) throws URISyntaxException {
		log.debug("REST request to update KPI : {}", kPIDTO);
		if (kPIDTO.getKpiId() == null) {
			return createKPI(kPIDTO);
		}
		KPIDTO result = kPIService.save(kPIDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kPIDTO.getKpiId().toString()))
				.body(result);
	}

	/**
	 * GET /kpis : get all the kPIS.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of kPIS in
	 *         body
	 */
	@RequestMapping(value = "/kpis",method = RequestMethod.GET)
	@Timed
	public List<KPIDTO> getAllKPIS() {
		log.debug("REST request to get all KPIS");
		return kPIService.findAll();
	}

	/**
	 * GET /kpis/:id : get the "id" kPI.
	 *
	 * @param id
	 *            the id of the kPIDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the kPIDTO,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/kpis/{id}",method = RequestMethod.GET)
	@Timed
	public ResponseEntity<KPIDTO> getKPI(@PathVariable Long id) {
		log.debug("REST request to get KPI : {}", id);
		KPIDTO kPIDTO = kPIService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kPIDTO));
	}

	/**
	 * DELETE /kpis/:id : delete the "id" kPI.
	 *
	 * @param id
	 *            the id of the kPIDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/kpis/{id}",method = RequestMethod.DELETE)
	@Timed
	public ResponseEntity<Void> deleteKPI(@PathVariable Long id) {
		log.debug("REST request to delete KPI : {}", id);
		kPIService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
