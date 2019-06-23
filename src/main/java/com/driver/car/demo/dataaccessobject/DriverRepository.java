package com.driver.car.demo.dataaccessobject;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.driver.car.demo.domainobject.DriverDO;
import com.driver.car.demo.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
    List<DriverDO> findByDeleted(boolean isDeleted);
}
