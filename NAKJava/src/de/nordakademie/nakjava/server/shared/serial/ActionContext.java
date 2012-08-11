package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.client.shared.ClientAction;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public abstract class ActionContext implements Serializable {
	// TODO: was tun wir hier???

	protected ServerAction serverAction;
	private List<ClientAction> preClientActions;
	private long batch;
	private boolean possiblyStable = false;

	protected ActionContext(long batch) {
		this.batch = batch;
		preClientActions = new LinkedList<>();
		try {
			serverAction = getAction();
		} catch (RemoteException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract ServerAction getAction() throws RemoteException;

	public void perform() throws RemoteException {
		for (ClientAction action : preClientActions) {
			action.perform();
		}
		serverAction.perform();
	}

	public void addPreClientAction(ClientAction action) {
		preClientActions.add(action);
	}

	@Override
	public boolean equals(Object obj) {
		return serverAction.equals(obj);
	}

	public long getBatch() {
		return batch;
	}

	public boolean isPossiblyStable() {
		return possiblyStable;
	}

	protected void setPossiblyStable(boolean stable) {
		possiblyStable = stable;
	}
}