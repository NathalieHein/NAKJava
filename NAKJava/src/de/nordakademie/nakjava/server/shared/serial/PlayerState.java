package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.client.shared.PlayerStateListener;

/**
 * Contains Listener on client-side and data that will be serialized to
 * client(PlayerModel, List of ActionContext)
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class PlayerState implements Serializable {
	private PlayerModel model;
	private List<ActionContext> actions;
	private long batch;
	private PlayerStateListener stateListener;
	private boolean dirty = false;

	public PlayerState(PlayerStateListener stateListener) {
		this.stateListener = stateListener;
		model = new PlayerModel();
	}

	public PlayerState(PlayerState state) {
		this.batch = state.batch;
		this.model = state.model;
		this.actions = state.actions;
		this.dirty = state.dirty;
	}

	public PlayerModel getModel() {
		return model;
	}

	public void setModel(PlayerModel model) {
		this.model = model;
		dirty = true;
	}

	public void setBatch(long batch) {
		this.batch = batch;
	}

	public long getBatch() {
		return batch;
	}

	public List<ActionContext> getActions() {
		return new ArrayList<ActionContext>(actions);
	}

	public void setActions(List<ActionContext> actions) {
		this.actions = actions;
		dirty = true;
	}

	/**
	 * called when server-processing of action is finished. New Thread is opened
	 * to invoke client via RMI. PlayerState is shallow-copied because of
	 * multithreading
	 */
	public void triggerChangeEvent() {
		final PlayerState playerState = new PlayerState(this);

		if (dirty) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						stateListener.stateChanged(playerState);

					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}
			}).start();

			dirty = false;
		}
	}
}
