package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.Action;

public class PlayerState implements Serializable {
	private PlayerModel model;
	private List<Action> actions;
	private PlayerStateListener stateListener;
	private boolean dirty = false;

	public PlayerState(PlayerStateListener stateListener) {
		this.stateListener = stateListener;
	}

	public PlayerModel getModel() {
		return model;
	}

	public void setModel(PlayerModel model) {
		this.model = model;
		dirty = true;
	}

	public List<Action> getActions() {
		return new ArrayList<Action>(actions);
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
		dirty = true;
	}

	public void triggerChangeEvent() {
		if (dirty) {
			try {
				stateListener.stateChanged(this);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dirty = false;
	}

}
