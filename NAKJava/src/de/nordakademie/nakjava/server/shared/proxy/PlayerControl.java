package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.Remote;

public interface PlayerControl extends Remote {

	public void triggerChangeEvent();

}
