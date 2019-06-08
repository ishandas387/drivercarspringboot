/**
 * 
 */
package com.mytaxi.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.CarDO;

/**
 * @author ishan
 *
 * Database Access Object for driver table.
 * <p/>
 */

public interface CarRepository extends CrudRepository<CarDO, Long> {

}
