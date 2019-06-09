package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.ManufacturerDO;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long> {
	
	List<ManufacturerDO> findByBrand(String brand);
}
