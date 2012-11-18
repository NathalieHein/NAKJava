package de.nordakademie.nakjava.util.classpathscanner;

/**
 * Interface for passing Acceptor Functions to the {@link ClasspathScanner}.
 * 
 * @author Kai
 * 
 * @param <T>
 */
public interface ClassAcceptor<T> {
	/**
	 * Returns wheter a loaded class is feasible or not.
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean acceptClass(Class<T> clazz);
}
