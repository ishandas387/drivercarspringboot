package com.driver.car.demo.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.driver.car.demo.domainobject.ManufacturerDO;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long> {
	
	List<ManufacturerDO> findByBrand(String brand);
}
