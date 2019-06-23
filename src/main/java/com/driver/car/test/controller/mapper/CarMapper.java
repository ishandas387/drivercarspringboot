/**
 * 
 */
package com.driver.car.test.controller.mapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.driver.car.test.datatransferobject.CarDTO;
import com.driver.car.test.domainobject.CarDO;

/**
 * @author ishan
 *
 */
public class CarMapper {

	/**
	 * Create a carDTO from DO
	 * @param CarDO
	 * @return
	 */
	public static CarDTO createCarDTO(CarDO CarDO) {
		CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder().setLicensePlate(CarDO.getLicensePlate())
				.setColour(CarDO.getColour()).setEngineType(CarDO.getEngineType())
				.setManufacturer(ManufacturerMapper.createDTO(CarDO.getManufacturer())).setModel(CarDO.getModel())
				.setClassification(CarDO.getClassification());
		return carDTOBuilder.createCarDTO();
	}

	/**
	 * Creates CarDO from DTO.
	 * Coordinates are not used as of now.
	 * @param carDTO
	 * @return
	 */
	public static CarDO makeCarDO(CarDTO carDTO) {
		if (null != carDTO) {
			CarDO carDo = new CarDO( ZonedDateTime.now(), carDTO.getLicensePlate(),
					carDTO.getSeatCount(), carDTO.getEngineType(), carDTO.getModel(),
					ManufacturerMapper.makeDO(carDTO.getManufacturer()), new Integer(0), carDTO.getClassification(),
					carDTO.getColour(), ZonedDateTime.now(), false);
			return carDo;
		}
		return null;
	}

	/**
	 * Deals with list.
	 * @param listOfCarDO
	 * @return
	 */
	public static List<CarDTO> createCarDTOList(List<CarDO> listOfCarDO) {
		if (null != listOfCarDO) {
			return listOfCarDO.stream().map(CarMapper::createCarDTO).collect(Collectors.toList());
		}
		return null;
	}

}
