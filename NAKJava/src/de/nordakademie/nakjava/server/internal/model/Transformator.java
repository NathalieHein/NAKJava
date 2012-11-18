package de.nordakademie.nakjava.server.internal.model;

/**
 * Transformator to transform a server-internal model-field into a
 * client-accessible VisibleField
 * 
 * @author Nathalie Hein (12154)
 * 
 * @param <from>in Model
 * @param <to>
 *            VisibleField
 */
public interface Transformator<from extends Object, to extends Object> {
	public to transform(from input);
}
