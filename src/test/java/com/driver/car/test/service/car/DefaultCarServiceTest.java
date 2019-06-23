/**
 * 
 */
package com.driver.car.test.service.car;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import com.driver.car.test.dataaccessobject.CarRepository;
import com.driver.car.test.dataaccessobject.ManufacturerRepository;
import com.driver.car.test.domainobject.CarDO;
import com.driver.car.test.domainobject.DriverDO;
import com.driver.car.test.domainobject.ManufacturerDO;
import com.driver.car.test.domainvalue.CarClassification;
import com.driver.car.test.domainvalue.OnlineStatus;
import com.driver.car.test.exception.CarAlreadyInUseException;
import com.driver.car.test.exception.ConstraintsViolationException;
import com.driver.car.test.exception.EntityNotFoundException;
import com.driver.car.test.service.car.DefaultCarService;
import com.driver.car.test.service.driver.DriverService;

/**
 * @author ishan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultCarServiceTest {
	@Mock
	private CarRepository carRepository;

	@Mock
	private DriverService driverService;

	@Mock
	private ManufacturerRepository manufacturerRepository;
	@InjectMocks
	private DefaultCarService defaultCarService;


	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#findCar(java.lang.Long)}.
	 */
	@Test
	public void testFindCar() throws Exception {
		CarDO car= new CarDO();
		car.setId(1L);
		car.setClassification(CarClassification.SEDAN);
		Optional<CarDO> carObject = Optional.of(car);
		Mockito.when(carRepository.findById(1L)).thenReturn(carObject );
		assertNotNull(defaultCarService.findCar(1L));
		assertEquals(CarClassification.SEDAN,defaultCarService.findCar(1L).getClassification());
	}

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#createCar(com.driver.car.test.domainobject.CarDO)}.
	 */
	@Test
	public void testCreateCar() throws Exception {
		Mockito.when(carRepository.save(any(CarDO.class))).thenReturn(new CarDO());
		CarDO createCar = defaultCarService.createCar(new CarDO());
		assertNotNull(createCar);
	}
	

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#createCar(com.driver.car.test.domainobject.CarDO)}.
	 */
	@Test(expected=ConstraintsViolationException.class)
	public void testCreateCarException() throws Exception {
		Mockito.when(carRepository.save(any(CarDO.class))).thenThrow(new DataIntegrityViolationException("conflict"));
		CarDO createCar = defaultCarService.createCar(new CarDO());
		assertNotNull(createCar);
	}

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#findAllCar()}.
	 */
	@Test
	public void testFindAllCar() throws Exception {
		Iterable<CarDO> carDTOList = new ArrayList<>();
		Mockito.when(carRepository.findAll()).thenReturn(carDTOList);
		assertNotNull(defaultCarService.findAllCar());
	}

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#delete(long)}.
	 */
	@Test
	public void testDelete() throws Exception {
		CarDO car= new CarDO();
		car.setId(1L);
		car.setClassification(CarClassification.SEDAN);
		DriverDO d = new DriverDO("","");
		d.setId(2L);
		car.setAllocatedDriver(d);
		
		Optional<CarDO> carObject = Optional.of(car);
		Mockito.when(carRepository.findById(1L)).thenReturn(carObject );
		DriverDO driverDO = new DriverDO("","");
		Mockito.when(driverService.find(2l)).thenReturn(driverDO);
		defaultCarService.delete(1L);
		 Mockito.verify(driverService).find(2L);
	}

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#getAllCarManufacturers()}.
	 */
	@Test
	public void testGetAllCarManufacturers() throws Exception {
		Iterable<ManufacturerDO> listOfManufacturer = new ArrayList<>();
		Mockito.when(manufacturerRepository.findAll()).thenReturn(listOfManufacturer);
		assertNotNull(defaultCarService.getAllCarManufacturers());
	}

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#updateCar(com.driver.car.test.domainobject.CarDO, java.lang.Long)}.
	 */
	@Test
	public void testUpdateCar() throws Exception {
		CarDO car= new CarDO();
		car.setId(1L);
		car.setClassification(CarClassification.SEDAN);
		ManufacturerDO manufacturer = new ManufacturerDO();
		manufacturer.setBrand("BMW");
		car.setManufacturer(manufacturer );
		Optional<CarDO> carObject = Optional.of(car);
		Mockito.when(carRepository.findById(1L)).thenReturn(carObject );
		List<ManufacturerDO> listOfmanufacturer = new ArrayList<>();
		ManufacturerDO m = new ManufacturerDO();
		m.setBrand("BMW");
		listOfmanufacturer.add(m );
		Mockito.when(manufacturerRepository.findByBrand("BMW")).thenReturn(listOfmanufacturer );
		defaultCarService.updateCar(car, 1L);
		 Mockito.verify(manufacturerRepository).findByBrand("BMW");
		
	}
	
	@Test
	public void testUpdateCarNullManuFacturer() throws Exception {
		CarDO car= new CarDO();
		car.setId(1L);
		car.setClassification(CarClassification.SEDAN);
		ManufacturerDO manufacturer = new ManufacturerDO();
		manufacturer.setBrand("BMW");
		car.setManufacturer(manufacturer );
		Optional<CarDO> carObject = Optional.of(car);
		Mockito.when(carRepository.findById(1L)).thenReturn(carObject );
		List<ManufacturerDO> listOfmanufacturer = new ArrayList<>();
		ManufacturerDO m = new ManufacturerDO();
		m.setBrand("BMW");
		listOfmanufacturer.add(m );
		Mockito.when(manufacturerRepository.findByBrand("BMW")).thenReturn(new ArrayList<>() );
		Mockito.when(manufacturerRepository.save(any(ManufacturerDO.class))).thenReturn(new ManufacturerDO());
		defaultCarService.updateCar(car, 1L);
		 Mockito.verify(manufacturerRepository).findByBrand("BMW");
		
	}

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#selectCarForDriver(java.lang.Long, java.lang.Long)}.
	 */
	@Test(expected =CarAlreadyInUseException.class)
	public void testSelectCarForDriverException() throws Exception {
		DriverDO driverDO = new DriverDO("","");
		driverDO.setId(2L);
		driverDO.setOnlineStatus(OnlineStatus.ONLINE);
		Mockito.when(driverService.find(2l)).thenReturn(driverDO);
		CarDO car= new CarDO();
		car.setId(1L);
		car.setClassification(CarClassification.SEDAN);
		ManufacturerDO manufacturer = new ManufacturerDO();
		manufacturer.setBrand("BMW");
		car.setManufacturer(manufacturer );
		car.setAllocatedDriver(driverDO);
		Optional<CarDO> carObject = Optional.of(car);
		Mockito.when(carRepository.findById(1L)).thenReturn(carObject );
		defaultCarService.selectCarForDriver(2l,1l);
	}
	

	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#selectCarForDriver(java.lang.Long, java.lang.Long)}.
	 */
	@Test(expected=EntityNotFoundException.class)
	public void testSelectCarForDriverExcpetion2() throws Exception {
		DriverDO driverDO = new DriverDO("","");
		driverDO.setId(2L);
		driverDO.setOnlineStatus(OnlineStatus.OFFLINE);
		Mockito.when(driverService.find(2l)).thenReturn(driverDO);
		CarDO car= new CarDO();
		car.setId(1L);
		car.setClassification(CarClassification.SEDAN);
		ManufacturerDO manufacturer = new ManufacturerDO();
		manufacturer.setBrand("BMW");
		car.setManufacturer(manufacturer );
		car.setAllocatedDriver(driverDO);
		Optional<CarDO> carObject = Optional.of(car);
		Mockito.when(carRepository.findById(1L)).thenReturn(carObject );
		defaultCarService.selectCarForDriver(2l,1l);
	}


	/**
	 * Test method for {@link com.driver.car.test.service.car.DefaultCarService#deselectCarForDriver(java.lang.Long)}.
	 */
	@Test
	public void testDeselectCarForDriver() throws Exception {
		DriverDO driverDO = new DriverDO("","");
		driverDO.setId(2L);
		driverDO.setOnlineStatus(OnlineStatus.OFFLINE);
		Mockito.when(driverService.find(2l)).thenReturn(driverDO);
		defaultCarService.deselectCarForDriver(2L);
	}

}
