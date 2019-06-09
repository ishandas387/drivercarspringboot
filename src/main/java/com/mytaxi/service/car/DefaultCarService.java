/**
 * 
 */
package com.mytaxi.service.car;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.util.CopyUtil;

/**
 * @author ishan
 *
 *         Service to encapsulate the link between DAO and controller and to
 *         have business logic for some car specific things.
 *         <p/>
 */

@Service
public class DefaultCarService implements CarService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

	private final CarRepository carRepository;
	private final DriverRepository driverRepository;
	private final ManufacturerRepository manufacturerRepository;

	@Autowired
	public DefaultCarService(final CarRepository carRepository, final DriverRepository driverRepository,
			ManufacturerRepository manufacturerRepository) {
		this.carRepository = carRepository;
		this.driverRepository = driverRepository;
		this.manufacturerRepository = manufacturerRepository;
	}

	/**
	 * Finds car
	 * 
	 * @throws EntityNotFoundException
	 *             when car not found.
	 */
	@Override
	public CarDO findCar(Long carId) throws EntityNotFoundException {

		return carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Car not found with Id " + carId));
	}

	/**
	 * Creates car.
	 * 
	 * @throws ConstraintsViolationException
	 * when carDO unique constraint not satisfied.
	 */
	@Override
	public CarDO createCar(CarDO car) throws ConstraintsViolationException {
		CarDO carDO;
		try {
			settingManufacturer(car);
			carDO = carRepository.save(car);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a car: {}", e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return carDO;
	}

	@Override
	public List<CarDO> findAllCar() {
		LOG.debug("getting all cars...");
		return (List<CarDO>) carRepository.findAll();
	}

	/**
	 * Deletes car
	 */
	@Override
	@Transactional
	public void delete(long carId) throws EntityNotFoundException {
		CarDO car = findCar(carId);
		if (null != car) {
			car.setDeleted(true);
			if (null != car.getAllocatedDriver()) {
				updateDriverSelectedCar(car.getAllocatedDriver().getId());
			}
		}
	}

	/**
	 * Update driver selected car
	 * 
	 * @param id
	 * @throws EntityNotFoundException
	 */
	@Transactional
	private void updateDriverSelectedCar(Long id) throws EntityNotFoundException {

		DriverDO driver = driverRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Driver Not found!"));
		if (null != driver) {
			driver.setSelectedCar(null);
		}

	}

	@Override
	public List<ManufacturerDO> getAllCarManufacturers() {
		LOG.debug("getting all manufacturers...");
		return (List<ManufacturerDO>) manufacturerRepository.findAll();
	}

	/**
	 * Updates car
	 */
	@Override
	@Transactional
	public void updateCar(CarDO makeCarDO, Long carId) throws EntityNotFoundException {
		CarDO foundCar = findCar(carId);
		try {
			if (null != foundCar) {
				settingManufacturer(makeCarDO);
				CopyUtil.copyNonNullProperties(foundCar, makeCarDO);
			}
		} catch (BeansException e) {
			LOG.error("Bean Exception while getting properties of car", makeCarDO, e);
		}
	}

	/**
	 * 
	 * @param makeCarDO
	 */
	private void settingManufacturer(CarDO makeCarDO) {
		if (null != makeCarDO.getManufacturer()) {
			ManufacturerDO manufacturer = makeCarDO.getManufacturer();
			manufacturer.setBrand(manufacturer.getBrand());
			List<ManufacturerDO> manufacturerByBrand = manufacturerRepository.findByBrand(manufacturer.getBrand());
			if (!manufacturerByBrand.isEmpty()) {
				LOG.info("Attaching the same...");
				makeCarDO.setManufacturer(manufacturerByBrand.get(0));
			} else {
				LOG.info("Manufacturer does not exist. Creating now...");
				ManufacturerDO savedManufacturer = manufacturerRepository.save(manufacturer);
				makeCarDO.setManufacturer(savedManufacturer);
			}
		}
	}

	@Override
	public void selectCarForDriver(Long driverId, Long carId) {
		
		
	}

}
