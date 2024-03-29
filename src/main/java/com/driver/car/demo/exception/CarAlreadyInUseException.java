package com.driver.car.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Car already in use.")
public class CarAlreadyInUseException extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CarAlreadyInUseException(String message)
    {
        super(message);
    }

}
