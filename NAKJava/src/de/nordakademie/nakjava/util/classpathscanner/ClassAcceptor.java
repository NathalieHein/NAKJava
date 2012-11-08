package de.nordakademie.nakjava.util.classpathscanner;

public interface ClassAcceptor<T> {
	public boolean acceptClass(Class<T> clazz);
}
