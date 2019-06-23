/**
 * 
 */
package com.driver.car.demo.service.car;

import java.util.List;

import com.driver.car.demo.domainobject.CarDO;
import com.driver.car.demo.domainobject.ManufacturerDO;
import com.driver.car.demo.exception.CarAlreadyInUseException;
import com.driver.car.demo.exception.ConstraintsViolationException;
import com.driver.car.demo.exception.EntityNotFoundException;

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
