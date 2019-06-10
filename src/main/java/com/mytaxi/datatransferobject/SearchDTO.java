package com.mytaxi.datatransferobject;

public class SearchDTO {
	
	
	private DriverSearchDTO driverSearchParams;
	private CarSearchDTO carSearchParams;
	
	public DriverSearchDTO getDriverSearchParams() {
		return driverSearchParams;
	}
	public void setDriverSearchParams(DriverSearchDTO driverSearchParams) {
		this.driverSearchParams = driverSearchParams;
	}
	public CarSearchDTO getCarSearchParams() {
		return carSearchParams;
	}
	public void setCarSearchParams(CarSearchDTO carSearchDTO) {
		this.carSearchParams = carSearchDTO;
	}
	

}
