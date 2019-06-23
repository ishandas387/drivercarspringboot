/**
 * 
 */
package com.driver.car.test.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author ishan
 *
 */
@Entity
@Table(name = "manufacturer", uniqueConstraints = @UniqueConstraint(name = "manufacturer_name_uc", columnNames = {
		"brand" }))
public class ManufacturerDO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	private Boolean deleted = false;

	@Column(nullable = false)
	@NotNull(message = "brand name cannot be null!")
	private String brand;
	

	@Column(nullable = false)
	@NotNull(message = "origin country name cannot be null!")
	private String countryRegistered;

	/**
	 * 
	 */
	public ManufacturerDO() {
	}
	

	/**
	 * @param id
	 * @param dateCreated
	 * @param deleted
	 * @param brand
	 * @param countryRegistered
	 */
	public ManufacturerDO(Long id, ZonedDateTime dateCreated,
			@NotNull(message = "brand name cannot be null!") String brand,
			@NotNull(message = "origin country name cannot be null!") String countryRegistered) {
		this.id = id;
		this.dateCreated = dateCreated;
		this.deleted = false;
		this.brand = brand;
		this.countryRegistered = countryRegistered;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}


	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getCountryRegistered() {
		return countryRegistered;
	}


	public void setCountryRegistered(String countryRegistered) {
		this.countryRegistered = countryRegistered;
	}

	
	
}
