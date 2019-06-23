package com.driver.car.test.service.driver;

import java.util.List;

import com.driver.car.test.datatransferobject.SearchDTO;
import com.driver.car.test.domainobject.DriverDO;
import com.driver.car.test.domainvalue.OnlineStatus;
import com.driver.car.test.exception.ConstraintsViolationException;
import com.driver.car.test.exception.DriverSearchException;
import com.driver.car.test.exception.EntityNotFoundException;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

	List<DriverDO> search(SearchDTO searchDTO) throws DriverSearchException;

}
