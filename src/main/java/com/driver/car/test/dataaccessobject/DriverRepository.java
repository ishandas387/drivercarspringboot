package com.driver.car.test.dataaccessobject;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.driver.car.test.domainobject.DriverDO;
import com.driver.car.test.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
    List<DriverDO> findByDeleted(boolean isDeleted);
}
