package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.internal.Batch;
import de.nordakademie.nakjava.server.shared.proxy.actions.InitAction;

public class PlayerState implements Serializable {
	private PlayerModel model;
	private List<ActionContext> actions;
	private PlayerStateListener stateListener;
	private boolean dirty = false;

	public PlayerState(PlayerStateListener stateListener) {
		this.stateListener = stateListener;
		model = new PlayerModel();
	}

	public PlayerModel getModel() {
		return model;
	}

	public void setModel(PlayerModel model) {
		this.model = model;
		dirty = true;
	}

	public List<ActionContext> getActions() {
		return new ArrayList<ActionContext>(actions);
	}

	public void setActions(List<ActionContext> actions) {
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

	public void initialize() {
		actions = new LinkedList<>();
		InitAction initAction = null;

		try {
			Batch.increaseBatchNr();
			initAction = new InitAction(Batch.getCurrentBatchNr());
			actions.add(initAction);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			initAction.perform();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
