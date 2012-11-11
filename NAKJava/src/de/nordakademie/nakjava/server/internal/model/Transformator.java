package de.nordakademie.nakjava.server.internal.model;

public interface Transformator<from extends Object, to extends Object> {
	public to transform(from input);
}
