package com.mytaxi.controller.mapper;

import java.time.ZonedDateTime;

import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.ManufacturerDO;

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
}
