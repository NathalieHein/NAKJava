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
	private boolean possiblyStable = false;

	protected ActionContext(long sessionNr) {
		preClientActions = new LinkedList<>();
		try {
			serverAction = getAction(sessionNr);
		} catch (RemoteException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO this is not a nice way of doing it (parameterized sessionNr for
	// creating the action), but couldn't think of any other
	// possible solution
	protected abstract ServerAction getAction(long sessionNr)
			throws RemoteException;

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

	public boolean isPossiblyStable() {
		return possiblyStable;
	}

	protected void setPossiblyStable(boolean stable) {
		possiblyStable = stable;
	}
}
