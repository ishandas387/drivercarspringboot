/**
 * 
 */
package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainvalue.CarClassification;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.GeoCoordinate;

/**
 * @author ishan
 *
 */
@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "license_plate_uc", columnNames = { "licensePlate" }))
public class CarDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(columnDefinition = "bigint default 0")
	private Long version;

	@Embedded
	private GeoCoordinate coordinate;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	@NotNull(message = "License plate number can not be null!")
	private String licensePlate;

	@Column(nullable = false)
	@NotNull(message = "Seat Count can not be null!")
	private Integer seatCount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Engine type can not be null!")
	private EngineType engineType;

	@Column(nullable = false)
	@NotNull(message = "Model can not be null!")
	private String model;

	@ManyToOne
	@JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
	@NotNull(message = "manufacturer_id can not be null!")
	private ManufacturerDO manufacturer;

	@Column(nullable = false)
	@NotNull(message = "Rating can not be null!")
	private Integer rating = 0;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Classification can not be null!")
	private CarClassification classification;

	@Column
	private String colour;

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime registrationDate;

	@Column(nullable = false)
	private Boolean deleted = false;
 
	@OneToOne
	@JoinColumn(name = "alloc_driver_id", referencedColumnName = "id")
	private DriverDO allocatedDriver;
	
	

	/**
	 * 
	 */
	public CarDO() {
	}

	/**
	 * @param coordinate
	 * @param dateCreated
	 * @param licensePlate
	 * @param seatCount
	 * @param engineType
	 * @param model
	 * @param manufacturer
	 * @param rating
	 * @param classification
	 * @param colour
	 * @param registrationDate
	 * @param deleted
	 * @param allocatedDriver
	 */
	public CarDO(GeoCoordinate coordinate, ZonedDateTime dateCreated,
			@NotNull(message = "License plate number can not be null!") String licensePlate,
			@NotNull(message = "Seat Count can not be null!") Integer seatCount,
			@NotNull(message = "Engine type can not be null!") EngineType engineType,
			@NotNull(message = "Model can not be null!") String model,
			@NotNull(message = "manufacturer_id can not be null!") ManufacturerDO manufacturer,
			@NotNull(message = "Rating can not be null!") Integer rating,
			@NotNull(message = "Classification can not be null!") CarClassification classification, String colour,
			ZonedDateTime registrationDate, Boolean deleted/*, DriverDTO allocatedDriver*/) {
		this.coordinate = coordinate;
		this.dateCreated = dateCreated;
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.engineType = engineType;
		this.model = model;
		this.manufacturer = manufacturer;
		this.rating = rating;
		this.classification = classification;
		this.colour = colour;
		this.registrationDate = registrationDate;
		this.deleted = deleted;
		/*this.allocatedDriver = DriverMapper.makeDriverDO(allocatedDriver);*/
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public GeoCoordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(GeoCoordinate coordinate) {
		this.coordinate = coordinate;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public ManufacturerDO getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(ManufacturerDO manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public CarClassification getClassification() {
		return classification;
	}

	public void setClassification(CarClassification classification) {
		this.classification = classification;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public ZonedDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(ZonedDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public DriverDO getAllocatedDriver() {
		return allocatedDriver;
	}

	public void setAllocatedDriver(DriverDO allocatedDriver) {
		this.allocatedDriver = allocatedDriver;
	}
	
	
	
}
