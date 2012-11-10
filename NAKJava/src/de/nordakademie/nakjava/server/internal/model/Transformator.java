package de.nordakademie.nakjava.server.internal.model;

public interface Transformator<from, to> {
	public to transform(from input);
}
