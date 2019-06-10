package com.mytaxi.datatransferobject;

import com.mytaxi.domainvalue.CarClassification;
import com.mytaxi.domainvalue.EngineType;

public class CarSearchDTO {
	
	private String licensePlate;
    private Integer seatCount;
    private EngineType engineType;
    private String model;
    private CarClassification classification;
    private String colour;
    private Integer minRating;
    
	public Integer getMinRating() {
		return minRating;
	}
	public void setMinRating(Integer minRating) {
		this.minRating = minRating;
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
}
