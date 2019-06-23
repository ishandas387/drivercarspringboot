/**
 * 
 */
package com.driver.car.test.service.car;

import java.util.List;

import com.driver.car.test.domainobject.CarDO;
import com.driver.car.test.domainobject.ManufacturerDO;
import com.driver.car.test.exception.CarAlreadyInUseException;
import com.driver.car.test.exception.ConstraintsViolationException;
import com.driver.car.test.exception.EntityNotFoundException;

/**
 * @author ishan
 *
 */
public interface CarService {

	CarDO findCar(Long carId) throws EntityNotFoundException;

	CarDO createCar(CarDO car) throws ConstraintsViolationException;

	List<CarDO> findAllCar();

	void delete(long carId) throws EntityNotFoundException;

	List<ManufacturerDO> getAllCarManufacturers();

	void updateCar(CarDO makeCarDO, Long carId) throws EntityNotFoundException;

	void selectCarForDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;

	void deselectCarForDriver(Long driverId) throws EntityNotFoundException;

}
