package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.client.shared.ClientAction;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.util.GlobalThreadPool;

/**
 * contains actionContext for client (dynamical status of game, what options the
 * client has next)
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class ActionContext implements Serializable,
		Comparable<ActionContext> {

	protected ServerAction serverAction;
	private List<ClientAction> preClientActions;

	/**
	 * 
	 * @param sessionNr
	 *            : unique session-identifier
	 */
	protected ActionContext(long sessionNr) {
		preClientActions = new LinkedList<>();
		try {
			serverAction = getAction(sessionNr);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sessionNr
	 *            : identifier of session that this actioncontext belongs to
	 * @return the proxy-ServerAction that is processed on the server after a
	 *         client request
	 * @throws RemoteException
	 */
	protected abstract ServerAction getAction(long sessionNr)
			throws RemoteException;

	/**
	 * invokes perform() method of embedded proxy-ServerAction
	 */
	public void perform() {
		for (ClientAction action : preClientActions) {
			action.perform();
		}
		GlobalThreadPool.perform(new Runnable() {

			@Override
			public void run() {
				try {
					serverAction.perform();
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void addPreClientAction(ClientAction action) {
		preClientActions.add(action);
	}

	@Override
	public boolean equals(Object obj) {
		return serverAction.equals(obj);
	}

	/**
	 * overriden from comparable-interface; used for sorting ActionContexts by
	 * name of class
	 */
	@Override
	public int compareTo(ActionContext otherActionContext) {
		return this.getClass().getSimpleName()
				.compareTo(otherActionContext.getClass().getSimpleName());
	}
}
