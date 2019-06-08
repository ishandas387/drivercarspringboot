/**
 * 
 */
package com.mytaxi.service.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * @author ishan
 *
 */
public interface CarService {

	  CarDO findCar(Long carId) throws EntityNotFoundException;

	  CarDO createCar(CarDO car) throws ConstraintsViolationException;
 
}
