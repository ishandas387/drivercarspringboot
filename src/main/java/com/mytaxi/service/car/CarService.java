/**
 * 
 */
package com.mytaxi.service.car;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

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

	void selectCarForDriver(Long driverId, Long carId);

}
