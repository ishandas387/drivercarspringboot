package com.driver.car.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong.")
public class DriverSearchException extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DriverSearchException(String message)
    {
        super(message);
    }

}
