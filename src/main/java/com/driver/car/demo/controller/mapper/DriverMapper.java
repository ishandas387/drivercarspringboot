package com.driver.car.demo.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.driver.car.demo.datatransferobject.DriverDTO;
import com.driver.car.demo.domainobject.CarDO;
import com.driver.car.demo.domainobject.DriverDO;
import com.driver.car.demo.domainvalue.GeoCoordinate;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    /**
     * Creates a driverDTO from DO
     * @param driverDO
     * @return
     */
    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }
       
       //adding car details to driver dto for better search result readability. 
       CarDO car = driverDO.getSelectedCar();
       if(null != car){
    	   driverDTOBuilder.setCar(CarMapper.createCarDTO(car));
       }

        return driverDTOBuilder.createDriverDTO();
    }

    /**
     * Deals with list.
     * @param drivers
     * @return
     */
    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }
}
