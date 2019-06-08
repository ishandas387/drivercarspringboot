/**
 * 
 */
package com.mytaxi.service.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * @author ishan
 *
 * Service to encapsulate the link between DAO and controller and to have business logic for some car specific things.
 * <p/>
 */
 
@Service
public class DefaultCarService implements CarService{
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;

    @Autowired
    public DefaultCarService(final CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }

	@Override
	public CarDO findCar(Long carId) throws EntityNotFoundException {
		
		return carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car not found with Id "+carId));
	}

	@Override
	public CarDO createCar(CarDO car) throws ConstraintsViolationException {
		return carRepository.save(car);
	}

}
