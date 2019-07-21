/**
 * 
 */
package com.driver.car.demo.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ishan
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDTO {

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private Long id;
	private String brand;
	private String countryRegistered;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * @return the countryRegistered
	 */
	public String getCountryRegistered() {
		return countryRegistered;
	}
	/**
	 * @param countryRegistered the countryRegistered to set
	 */
	public void setCountryRegistered(String countryRegistered) {
		this.countryRegistered = countryRegistered;
	}
	/**
	 * @param id
	 * @param brand
	 * @param countryRegistered
	 */
	public ManufacturerDTO(Long id, String brand, String countryRegistered) {
		this.id = id;
		this.brand = brand;
		this.countryRegistered = countryRegistered;
	}
	/**
	 * 
	 */
	public ManufacturerDTO() {
		super();
	}
	
	
	

}
