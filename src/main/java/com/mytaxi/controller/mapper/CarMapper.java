/**
 * 
 */
package com.mytaxi.controller.mapper;

import java.time.ZonedDateTime;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.GeoCoordinate;

/**
 * @author ishan
 *
 */
public class CarMapper {

	public static CarDTO createCarDTO(CarDO CarDO) {
		CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder().setLicensePlate(CarDO.getLicensePlate())
				.setColour(CarDO.getColour()).setEngineType(CarDO.getEngineType())
				.setManufacturer(ManufacturerMapper.createDTO(CarDO.getManufacturer())).setModel(CarDO.getModel())
				.setClassification(CarDO.getClassification());
		return carDTOBuilder.createCarDTO();
	}

	public static CarDO makeCarDO(CarDTO carDTO) {
		if (null != carDTO) {
			CarDO carDo = new CarDO(new GeoCoordinate(66d, 71d), ZonedDateTime.now(), carDTO.getLicensePlate(),
					carDTO.getSeatCount(), carDTO.getEngineType(), carDTO.getModel(),
					ManufacturerMapper.makeDO(carDTO.getManufacturer()), new Integer(0), carDTO.getClassification(),
					carDTO.getColour(), ZonedDateTime.now(), false);
			return carDo;
		}
		return null;
	}

}
