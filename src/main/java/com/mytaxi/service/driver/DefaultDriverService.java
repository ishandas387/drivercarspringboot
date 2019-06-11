package com.mytaxi.service.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.datatransferobject.CarSearchDTO;
import com.mytaxi.datatransferobject.DriverSearchDTO;
import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverSearchException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final DriverRepository driverRepository;

	@Autowired
	private EntityManager entityManager;

	public DefaultDriverService(final DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	/**
	 * Selects a driver by id.
	 *
	 * @param driverId
	 * @return found driver
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	public DriverDO find(Long driverId) throws EntityNotFoundException {
		return findDriverChecked(driverId);
	}

	/**
	 * Creates a new driver.
	 *
	 * @param driverDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a driver already exists with the given username, ... .
	 */
	@Override
	public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
		DriverDO driver;
		try {
			driver = driverRepository.save(driverDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return driver;
	}

	/**
	 * Deletes an existing driver by id.
	 *
	 * @param driverId
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long driverId) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setDeleted(true);
	}

	/**
	 * Update the location for a driver.
	 *
	 * @param driverId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
	}

	/**
	 * Find all drivers by online state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<DriverDO> find(OnlineStatus onlineStatus) {
		return driverRepository.findByOnlineStatus(onlineStatus);
	}

	private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
		return driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
	}

	/**
	 * search service
	 * @param searchDTO
	 * @return List<DriverDO> after filtering.
	 * @throws DriverSearchException 
	 */
	@Override
	public List<DriverDO> search(SearchDTO searchDTO) throws DriverSearchException {
		List<DriverDO> listOfDriverDO = new ArrayList<>();
		Map searchMap = new ObjectMapper().convertValue(searchDTO, Map.class);

		// if no values are sent. search results defaults to all not deleted
		// drivers.
		try{
			
			if (null == searchDTO
					|| (null == searchDTO.getDriverSearchParams() && null == searchDTO.getCarSearchParams())) {
				return (List<DriverDO>) driverRepository.findByDeleted(Boolean.FALSE);
			}
			// if only driver filter criteria is available. search result defaults
			// to
			// driver.
			else if (null != searchDTO.getDriverSearchParams() && null == searchDTO.getCarSearchParams()) {
				listOfDriverDO = getDetailsByDriverParams(searchDTO.getDriverSearchParams(), new ArrayList<>());
			}
			// if car criteria is present, search with car details first.
			else if (null != searchDTO.getCarSearchParams()) {
				
				listOfDriverDO = getDetailsByCarParams(searchDTO);
				
				// refine search with driverIds
				if (null != searchDTO.getDriverSearchParams()) {
					List<Long> collectedDriverIdsFilteredByCar = listOfDriverDO.stream().map(DriverDO::getId)
							.collect(Collectors.toList());
					listOfDriverDO = getDetailsByDriverParams(searchDTO.getDriverSearchParams(),
							collectedDriverIdsFilteredByCar);
				}
			}
		}
		catch(Exception e){
			LOG.error("Exception while searching drivers",e.getMessage());
			throw new DriverSearchException(e.getMessage());
		}

		return listOfDriverDO;
	}

	/**
	 * Dynamic query with driver params.
	 * @param driverSearchParams
	 * @param driverIds, used when car filter has the specific driver ids in place
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<DriverDO> getDetailsByDriverParams(DriverSearchDTO driverSearchParams, List<Long> driverIds) {
		Query driverQuery = getDriverDOQuery(driverSearchParams,driverIds);
		setDriverParams(driverSearchParams, driverQuery, driverIds);
		return (List<DriverDO>) driverQuery.getResultList();
	}

	/**
	 * Sets params for Advanced searchDO JPQL.
	 * 
	 * @param searchDTO
	 * @param driverQuery
	 */
	private void setDriverParams(DriverSearchDTO driverSearchParams, Query driverQuery, List<Long> driverIds) {
		if (null != driverSearchParams.getUserName()) {
			driverQuery.setParameter("username", driverSearchParams.getUserName());
		}
		if (null != driverSearchParams.getOnlineStatus()) {
			driverQuery.setParameter("onlineStatus", driverSearchParams.getOnlineStatus());
		}
		if (!driverIds.isEmpty()) {
			driverQuery.setParameter("driverIds", driverIds);
		}
	}

	/**
	 * Creates DriverDO query for advanced search.
	 * 
	 * @param DriverSearchDTO
	 * @return
	 */
	private Query getDriverDOQuery(DriverSearchDTO driverSearchParams, List<Long> driverIds) {
		StringBuilder qryBuilder = new StringBuilder("from DriverDO d where d.deleted is false");
		if (!driverIds.isEmpty()) {
			qryBuilder.append(" and d.id in (:driverIds) ");
		}
		if (null != driverSearchParams.getUserName())
			qryBuilder.append(" and d.username=:username ");
		if (null != driverSearchParams.getOnlineStatus())
			qryBuilder.append(" and d.onlineStatus=:onlineStatus ");
		return entityManager.createQuery(qryBuilder.toString());
	}

	/**
	 * 
	 * @param searchDTO
	 * @return List<DriverDO> from filtered carDOS 
	 */
	@SuppressWarnings("unchecked")
	private List<DriverDO> getDetailsByCarParams(SearchDTO searchDTO) {

		Query carQuery = createDynamicCarQuery(searchDTO.getCarSearchParams());
		List<CarDO> carDOs = null;
		ArrayList<DriverDO> driverDOS = new ArrayList<>();

		setQueryParametersForCar(searchDTO.getCarSearchParams(), carQuery);
		carDOs = (List<CarDO>) carQuery.getResultList();
		if (CollectionUtils.isEmpty(carDOs))
			return driverDOS;
		else {
			carDOs.stream().filter(carDO -> null != carDO.getAllocatedDriver())
					.forEach(carDO -> driverDOS.add(carDO.getAllocatedDriver()));
			return driverDOS;
		}

	}

	/**
	 * Setting parameters to dynamic car query.
	 * @param carSearchDTO
	 * @param carQuery
	 */
	private void setQueryParametersForCar(CarSearchDTO carSearchDTO, Query carQuery) {
		if (null != carSearchDTO.getClassification()) {
			carQuery.setParameter("classification", carSearchDTO.getClassification());
		}
		if (null != carSearchDTO.getEngineType()) {
			carQuery.setParameter("engineType", carSearchDTO.getEngineType());
		}
		if (null != carSearchDTO.getColour()) {
			carQuery.setParameter("colour", carSearchDTO.getColour());
		}
		if (null != carSearchDTO.getLicensePlate()) {
			carQuery.setParameter("licensePlate", carSearchDTO.getLicensePlate());
		}
		if (null != carSearchDTO.getSeatCount()) {
			carQuery.setParameter("seatCount", carSearchDTO.getSeatCount());
		}
		if (null != carSearchDTO.getMinRating()) {
			carQuery.setParameter("minRating", carSearchDTO.getMinRating());
		}
	}

	/**
	 * Creating dynamic car query
	 * @param carSearchDTO
	 * @return
	 */
	private Query createDynamicCarQuery(CarSearchDTO carSearchDTO) {
		StringBuilder qryBuilder = new StringBuilder();
		qryBuilder.append("from CarDO c where c.deleted is false ");
		qryBuilder.append(" and allocatedDriver is not null");
		if (null != carSearchDTO.getLicensePlate())
			qryBuilder.append(" and c.licensePlate=:licensePlate ");
		if (null != carSearchDTO.getClassification())
			qryBuilder.append(" and c.classification=:classification");
		if (null != carSearchDTO.getEngineType())
			qryBuilder.append(" and c.engineType=:engineType ");
		if (null != carSearchDTO.getColour())
			qryBuilder.append(" and c.colour=:colour ");
		if (null != carSearchDTO.getSeatCount())
			qryBuilder.append(" and c.seatCount=:seatCount ");
		if (null != carSearchDTO.getMinRating())
			qryBuilder.append(" and c.rating >= :minRating");
		return entityManager.createQuery(qryBuilder.toString());
	}

}
