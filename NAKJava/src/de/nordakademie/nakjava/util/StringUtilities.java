package de.nordakademie.nakjava.util;

import java.util.Collection;

public class StringUtilities {
	public static boolean isNotNullOrEmpty(String toCheck) {
		return toCheck != null && !"".equals(toCheck);
	}

	public static String concatWithDelimiter(
			Collection<? extends Object> toSplit, String delimiter) {
		StringBuffer sb = new StringBuffer();
		boolean first = true;

		for (Object split : toSplit) {

			if (first) {
				first = false;
			} else {
				sb.append(delimiter);
			}

			sb.append(split.toString());

		}

		return sb.toString();

	}
}
