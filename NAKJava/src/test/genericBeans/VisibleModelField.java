package test.genericBeans;

import java.util.Map;

public class VisibleModelField<T> {

	private String identifier;

	public VisibleModelField(String identifier) {
		this.identifier = identifier;
	}

	public T getValue(Map<String, Object> values) {
		return (T) values.get(identifier);
	}
}
