package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.proxy.Action;

public class PlayerState implements Serializable {
	private PlayerModel model;
	private List<Action> actions;

	public PlayerModel getModel() {
		return model;
	}

	public void setModel(PlayerModel model) {
		this.model = model;
	}

	public List<Action> getActions() {
		return new ArrayList<Action>(actions);
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

}
