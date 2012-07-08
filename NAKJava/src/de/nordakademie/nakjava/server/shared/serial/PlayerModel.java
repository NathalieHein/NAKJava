package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;

public class PlayerModel implements Serializable {
	private String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
