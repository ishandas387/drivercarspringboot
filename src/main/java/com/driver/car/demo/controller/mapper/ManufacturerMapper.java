package com.driver.car.demo.controller.mapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.driver.car.demo.datatransferobject.ManufacturerDTO;
import com.driver.car.demo.domainobject.ManufacturerDO;

public class ManufacturerMapper {
	private ManufacturerMapper() {
	}

	public static ManufacturerDTO createDTO(ManufacturerDO manufacturerDO) {
		if (null != manufacturerDO) {
			return new ManufacturerDTO(manufacturerDO.getId(), manufacturerDO.getBrand(),
					manufacturerDO.getCountryRegistered());
		}
		return null;
	}

	public static ManufacturerDO makeDO(ManufacturerDTO manufacturerDTO) {
		if (null != manufacturerDTO) {
			return new ManufacturerDO(manufacturerDTO.getId(), ZonedDateTime.now(), manufacturerDTO.getBrand(),
					manufacturerDTO.getCountryRegistered());
		}
		return null;
	}
	   public static List<ManufacturerDTO> makeManufacturerDTOList(List<ManufacturerDO> manufacturers){
	        return manufacturers.stream()
	                .map(ManufacturerMapper::createDTO)
	                .collect(Collectors.toList());
	    }
}
