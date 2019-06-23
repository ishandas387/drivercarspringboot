/**
 * 
 */
package com.driver.car.demo.service.car;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.driver.car.demo.dataaccessobject.CarRepository;
import com.driver.car.demo.dataaccessobject.ManufacturerRepository;
import com.driver.car.demo.domainobject.CarDO;
import com.driver.car.demo.domainobject.DriverDO;
import com.driver.car.demo.domainobject.ManufacturerDO;
import com.driver.car.demo.domainvalue.OnlineStatus;
import com.driver.car.demo.exception.CarAlreadyInUseException;
import com.driver.car.demo.exception.ConstraintsViolationException;
import com.driver.car.demo.exception.EntityNotFoundException;
import com.driver.car.demo.service.driver.DriverService;
import com.driver.car.demo.util.CopyUtil;

/**
 * @author ishan
 *
 *         Service to encapsulate the link between DAO and controller and to
 *         have business logic for some car specific things.
 *         <p/>
 */

@Service
public class DefaultCarService implements CarService {

	private static final String BEAN_EXCEPTION_WHILE_GETTING_PROPERTIES_OF_CAR = "Bean Exception while getting properties of car";

	private static final String GETTING_ALL_MANUFACTURERS = "getting all manufacturers...";

	private static final String GETTING_ALL_CARS = "getting all cars...";

	private static final String CONSTRAINTS_VIOLATION_EXCEPTION_WHILE_CREATING_A_CAR = "ConstraintsViolationException while creating a car: {}";

	private static final String CAR_NOT_FOUND_WITH_ID = "Car not found with Id ";

	private static final String ATTACHING_THE_SAME = "Attaching the same...";

	private static final String MANUFACTURER_DOES_NOT_EXIST_CREATING_NOW = "Manufacturer does not exist. Creating now...";

	private static final String CAR_IS_ALREADY_IN_USE = "Car is already in use";

	private static final String DRIVER_OFFLINE = "Driver offline";

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

	private final CarRepository carRepository;
	private final DriverService driverService;
	private final ManufacturerRepository manufacturerRepository;

	@Autowired
	public DefaultCarService(final CarRepository carRepository, final DriverService driverService,
			ManufacturerRepository manufacturerRepository) {
		this.carRepository = carRepository;
		this.driverService = driverService;
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
				.orElseThrow(() -> new EntityNotFoundException(CAR_NOT_FOUND_WITH_ID + carId));
	}

	/**
	 * Creates car.
	 * 
	 * @throws ConstraintsViolationException
	 *             when carDO unique constraint not satisfied.
	 */
	@Override
	public CarDO createCar(CarDO car) throws ConstraintsViolationException {
		CarDO carDO;
		try {
			settingManufacturer(car);
			carDO = carRepository.save(car);
		} catch (DataIntegrityViolationException e) {
			LOG.warn(CONSTRAINTS_VIOLATION_EXCEPTION_WHILE_CREATING_A_CAR, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return carDO;
	}

	@Override
	public List<CarDO> findAllCar() {
		LOG.debug(GETTING_ALL_CARS);
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

		DriverDO driver = driverService.find(id);
		if (null != driver) {
			driver.setSelectedCar(null);
		}

	}

	@Override
	public List<ManufacturerDO> getAllCarManufacturers() {
		LOG.debug(GETTING_ALL_MANUFACTURERS);
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
			LOG.error(BEAN_EXCEPTION_WHILE_GETTING_PROPERTIES_OF_CAR, makeCarDO, e);
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
				LOG.info(ATTACHING_THE_SAME);
				makeCarDO.setManufacturer(manufacturerByBrand.get(0));
			} else {
				LOG.info(MANUFACTURER_DOES_NOT_EXIST_CREATING_NOW);
				ManufacturerDO savedManufacturer = manufacturerRepository.save(manufacturer);
				makeCarDO.setManufacturer(savedManufacturer);
			}
		}
	}

	@Override
	@Transactional
	public void selectCarForDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException {
		DriverDO driver = driverService.find(driverId);
		CarDO car = this.findCar(carId);
		if (null != driver) {
			if (!carInUse(car)) {
				allocateIfOnline(driver, car);
			}else{
				throw new CarAlreadyInUseException(CAR_IS_ALREADY_IN_USE);
			}

		}

	}

	private void allocateIfOnline(DriverDO driver, CarDO car) throws EntityNotFoundException {
		if (OnlineStatus.ONLINE.equals(driver.getOnlineStatus())) {
			driver.setSelectedCar(car);
			car.setAllocatedDriver(driver);
		}else{
			throw new EntityNotFoundException(DRIVER_OFFLINE);
		}
	}

	private boolean carInUse(CarDO car) {
		return (null != car.getAllocatedDriver()
				&& OnlineStatus.ONLINE.equals(car.getAllocatedDriver().getOnlineStatus()));
	}

	@Override
	@Transactional
	public void deselectCarForDriver(Long driverId) throws EntityNotFoundException {

		DriverDO driver = driverService.find(driverId);
		if (null != driver) {
			driver.setSelectedCar(null);
		}
	}

}
