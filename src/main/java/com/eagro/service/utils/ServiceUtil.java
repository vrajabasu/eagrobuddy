package com.eagro.service.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ServiceUtil {
	/**
	 * Checks if is empty (null or zero length).
	 *
	 * @param stringToCheck
	 *            the string to check
	 * @return true, if is empty
	 */
	public static boolean isEmpty(final String stringToCheck) {
		return StringUtils.isEmpty(stringToCheck);
	}

	/**
	 * Checks if is not empty (null or zero length).
	 *
	 * @param stringToCheck
	 *            the string to check
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(final String stringToCheck) {
		return StringUtils.isNotEmpty(stringToCheck);
	}

	/**
	 * Checks if is not empty result.
	 *
	 * @param result
	 *            the result
	 * @return true, if is not empty result
	 */
	public static boolean isNotEmptyResult(final List<?> result) {
		final Object o = null;
		return (result != null) && !result.isEmpty() && !result.contains(o);
	}
	
	/**
	 * Compare Two list.
	 *
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return true, if successful
	 */
	public static boolean compare(List<?> list1, List<?> list2) {
		
		if(list1 == null || list2 == null)
			return false;
		
		if(list1.size() != list2.size())
			return false;
		
		if(list1.containsAll(list2))
			return true;
		
		return false;
	}
}
