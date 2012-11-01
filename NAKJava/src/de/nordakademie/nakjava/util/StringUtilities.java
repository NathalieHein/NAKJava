package de.nordakademie.nakjava.util;

public class StringUtilities {
	public static boolean isNotNullOrEmpty(String toCheck) {
		return toCheck != null && !"".equals(toCheck);
	}
}
