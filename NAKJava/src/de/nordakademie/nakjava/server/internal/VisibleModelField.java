package de.nordakademie.nakjava.server.internal;

import java.util.Map;

public class VisibleModelField<T> {

	private String identifier;
	private VisibleModelField<?> predecessor;
	private ClientFieldTransformer<Object, T> transformer;

	public VisibleModelField(String identifier) {
		this.identifier = identifier;
	}

	private VisibleModelField(VisibleModelField predecessor,
			ClientFieldTransformer transformer) {
		this.predecessor = predecessor;
		this.transformer = transformer;
	}

	public <U> VisibleModelField<U> setTransformer(
			ClientFieldTransformer<T, U> transformer) {
		return new VisibleModelField<U>(this, transformer);
	}

	public T getValue(Map<String, Object> values) {
		if (transformer == null) {
			return (T) values.get(identifier);
		} else {
			return transformer.transform(predecessor.getValue(values));
		}
	}

	public interface ClientFieldTransformer<from, to> {
		public to transform(from value);
	}
}
