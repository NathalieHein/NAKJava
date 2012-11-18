package de.nordakademie.nakjava.client.internal.gui;

import java.util.Map;

import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.internal.VisibleModelField;

/**
 * A valueHolder describes a GUI element which is the representation for a
 * passed value from the server. The element may choose itself which element is
 * feasible.
 * 
 */
public interface ValueHolder {
	/**
	 * This method is invoked everytime new values are submitted in a generic
	 * way. A {@link VisibleModelField} in {@link VisibleModelFields}
	 * (generated) may be used as an typesafe accessor for the passed map.
	 * 
	 * @param genericValues
	 */
	public void pickValue(Map<String, Object> genericValues);
}
