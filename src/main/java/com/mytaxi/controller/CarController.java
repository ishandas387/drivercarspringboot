/**
 * 
 */
package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.controller.mapper.ManufacturerMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;

/**
 * @author ishan All operations with a car will be routed by this controller.
 *         <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController {

	private final CarService carService;

	@Autowired
	public CarController(final CarService carService) {
		this.carService = carService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
		return CarMapper.createCarDTO(carService.createCar(CarMapper.makeCarDO(carDTO)));
	}

	@GetMapping("/{carId}")
	public CarDTO getCar(@PathVariable Long carId) throws EntityNotFoundException {
		return CarMapper.createCarDTO(carService.findCar(carId));
	}

	@GetMapping
	public List<CarDTO> getAllCars() {
		return CarMapper.createCarDTOList(carService.findAllCar());
	}

	@DeleteMapping("/{carId}")
	public void deleteCar(@PathVariable long carId) throws EntityNotFoundException {
		carService.delete(carId);
	}

	@PutMapping("/{carId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateCar(@PathVariable Long carId, CarDTO carDTO) throws EntityNotFoundException {
		carService.updateCar(CarMapper.makeCarDO(carDTO), carId);
	}

	@GetMapping("/manufacturers")
	public List<ManufacturerDTO> getAllManufacturers() {
		return ManufacturerMapper.makeManufacturerDTOList(carService.getAllCarManufacturers());
	}

}
