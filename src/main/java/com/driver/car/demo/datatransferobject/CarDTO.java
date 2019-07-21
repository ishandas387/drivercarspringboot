/**
 * 
 */
package com.driver.car.demo.datatransferobject;

import javax.validation.constraints.NotNull;

import com.driver.car.demo.domainvalue.CarClassification;
import com.driver.car.demo.domainvalue.EngineType;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ishan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
	

	@NotNull(message = "LicensePlate can not be null!")
	private String licensePlate;
	@NotNull(message = "Seat Count can not be null!")
    private Integer seatCount;
	@NotNull(message = "engineType can not be null!")
    private EngineType engineType;
	@NotNull(message = "manufacturer can not be null!")
    private ManufacturerDTO manufacturer;
	@NotNull(message = "model can not be null!")
    private String model;
	@NotNull(message = "classification can not be null!")
    private CarClassification classification;
	@NotNull(message = "colour can not be null!")
    private String colour;
	/**
	 * @return the licensePlate
	 */
	public String getLicensePlate() {
		return licensePlate;
	}
	/**
	 * @param licensePlate the licensePlate to set
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	/**
	 * @return the seatCount
	 */
	public Integer getSeatCount() {
		return seatCount;
	}
	/**
	 * @param seatCount the seatCount to set
	 */
	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}
	/**
	 * @return the engineType
	 */
	public EngineType getEngineType() {
		return engineType;
	}
	/**
	 * @param engineType the engineType to set
	 */
	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}
	/**
	 * @return the manufacturer
	 */
	public ManufacturerDTO getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(ManufacturerDTO manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the classification
	 */
	public CarClassification getClassification() {
		return classification;
	}
	/**
	 * @param classification the classification to set
	 */
	public void setClassification(CarClassification classification) {
		this.classification = classification;
	}
	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}
	/**
	 * @param colour the colour to set
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	
	public CarDTO() {
	}
	/**
	 * @param licensePlate
	 * @param seatCount
	 * @param convertible
	 * @param engineType
	 * @param manufacturer
	 * @param model
	 * @param classification
	 * @param colour
	 */
	public CarDTO(String licensePlate, Integer seatCount, EngineType engineType,
			ManufacturerDTO manufacturer, String model, CarClassification classification, String colour) {
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.engineType = engineType;
		this.manufacturer = manufacturer;
		this.model = model;
		this.classification = classification;
		this.colour = colour;
	}

    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }
	public static class CarDTOBuilder {
		private String licensePlate;
	    private Integer seatCount;
	    private EngineType engineType;
	    private ManufacturerDTO manufacturer;
	    private String model;
	    private CarClassification classification;
	    private String colour;
	    
		
	    public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }
	    
	    public CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }
	    public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }
	    public CarDTOBuilder setManufacturer(ManufacturerDTO manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }
	    public CarDTOBuilder setModel(String model)
        {
            this.model = model;
            return this;
        }
	    public CarDTOBuilder setClassification(CarClassification classification)
        {
            this.classification = classification;
            return this;
        }
	    public CarDTOBuilder setColour(String colour)
        {
            this.colour = colour;
            return this;
        }
	    
	    public CarDTO createCarDTO(){
	    	return new CarDTO(licensePlate, seatCount, engineType, manufacturer, model, classification, colour);
	    }
	}
	
    
    
}
