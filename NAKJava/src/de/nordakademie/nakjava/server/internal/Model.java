package de.nordakademie.nakjava.server.internal;

public class Model {
	private static Model instance;

	private boolean x;
	private boolean y;
	private String name;

	private Model() {
		setX(false);
		setY(true);
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

	public boolean isX() {
		return x;
	}

	public void setX(boolean x) {
		this.x = x;
	}

	public boolean isY() {
		return y;
	}

	public void setY(boolean y) {
		this.y = y;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
