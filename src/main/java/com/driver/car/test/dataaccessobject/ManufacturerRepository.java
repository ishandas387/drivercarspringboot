package com.driver.car.test.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.driver.car.test.domainobject.ManufacturerDO;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long> {
	
	List<ManufacturerDO> findByBrand(String brand);
}
