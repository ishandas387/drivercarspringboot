/**
 * 
 */
package com.driver.car.test.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.driver.car.test.domainobject.CarDO;

/**
 * @author ishan
 *
 * Database Access Object for driver table.
 * <p/>
 */

public interface CarRepository extends CrudRepository<CarDO, Long> {

}
