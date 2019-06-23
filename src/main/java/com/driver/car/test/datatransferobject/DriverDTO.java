package com.driver.car.test.datatransferobject;

import javax.validation.constraints.NotNull;

import com.driver.car.test.domainvalue.GeoCoordinate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;
    
    private CarDTO car;


    private DriverDTO()
    {
    }


   

	private DriverDTO(Long id, String username, String password, GeoCoordinate coordinate, CarDTO car)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.car= car;
    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }

    public CarDTO getCar() {
		return car;
	}


	public void setCar(CarDTO car) {
		this.car = car;
	}

    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }

    public static class DriverDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        private CarDTO car;


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }

        public DriverDTOBuilder setCar(CarDTO car)
        {
            this.car = car;
            return this;
        }


        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(id, username, password, coordinate,car);
        }

    }
}
