package com.driver.car.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.driver.car.demo.controller.mapper.DriverMapper;
import com.driver.car.demo.datatransferobject.DriverDTO;
import com.driver.car.demo.datatransferobject.SearchDTO;
import com.driver.car.demo.domainobject.DriverDO;
import com.driver.car.demo.domainvalue.OnlineStatus;
import com.driver.car.demo.exception.CarAlreadyInUseException;
import com.driver.car.demo.exception.ConstraintsViolationException;
import com.driver.car.demo.exception.DriverSearchException;
import com.driver.car.demo.exception.EntityNotFoundException;
import com.driver.car.demo.service.car.CarService;
import com.driver.car.demo.service.driver.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;
    private final CarService carService;

    @Autowired
    public DriverController(final DriverService driverService,final CarService carService)
    {
        this.driverService = driverService;
        this.carService= carService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }
    
    @PutMapping("/carselect")
    public void selectCar(@RequestParam Long driverId, @RequestParam Long carId) throws  EntityNotFoundException, CarAlreadyInUseException {
        carService.selectCarForDriver(driverId,carId);
    }
    
    @DeleteMapping("/cardeselect")
    public void deselectCar(@RequestParam Long driverId, @RequestParam Long carId) throws  EntityNotFoundException {
        carService.deselectCarForDriver(driverId);
    }
    
    @GetMapping("/search")
    public List<DriverDTO> search(SearchDTO searchDTO) throws DriverSearchException
    {
        return DriverMapper.makeDriverDTOList(driverService.search(searchDTO));
    } 

}
