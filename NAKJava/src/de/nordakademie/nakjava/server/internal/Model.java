package de.nordakademie.nakjava.server.internal;


public class Model {
	private static Model instance;

	private Model() {

	}

	/**
	 * {@link ActionBroker} ensures serializable transaction-handling for
	 * {@link Action}s
	 * 
	 * @return
	 */
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
}
