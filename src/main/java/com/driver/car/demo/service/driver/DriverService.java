package com.driver.car.demo.service.driver;

import java.util.List;

import com.driver.car.demo.datatransferobject.SearchDTO;
import com.driver.car.demo.domainobject.DriverDO;
import com.driver.car.demo.domainvalue.OnlineStatus;
import com.driver.car.demo.exception.ConstraintsViolationException;
import com.driver.car.demo.exception.DriverSearchException;
import com.driver.car.demo.exception.EntityNotFoundException;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

	List<DriverDO> search(SearchDTO searchDTO) throws DriverSearchException;

}
