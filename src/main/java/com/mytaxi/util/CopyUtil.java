package com.mytaxi.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;

public class CopyUtil {

	private static final Logger LOG = LoggerFactory.getLogger(CopyUtil.class);

	private CopyUtil(){}

	/**
	 * Utility to copy non-null properties from source bean to target bean
	 * 
	 * @param target
	 * @param src
	 * @param <T>
	 * @return
	 */
	public static <T, S> T copyNonNullProperties(T target, S src) {
		if (src == null || target == null)
			return null;

		final BeanWrapper srcBeanWrapper = new BeanWrapperImpl(src);
		final BeanWrapper trg = new BeanWrapperImpl(target);

		for (final Field property : target.getClass().getDeclaredFields()) {
			try {
				Object providedObject = srcBeanWrapper.getPropertyValue(property.getName());
				if (providedObject != null) {
					trg.setPropertyValue(property.getName(), providedObject);
				}
			} catch (NotReadablePropertyException e) {
				LOG.debug("Ignoring NotReadablePropertyException for:" + property.getName());
			}
		}
		return target;
	}
}
