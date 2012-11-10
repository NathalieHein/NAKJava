package test.genericBeans;

import java.util.Map;

public class VisibleModelField<T> {

	private String identifier;
	private Class<T> type;

	public VisibleModelField(String identifier, String className) {
		this.identifier = identifier;
		try {
			this.type = (Class<T>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public T getValue(Map<String, Object> values) {
		return (T) values.get(identifier);
	}
}
