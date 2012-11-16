package de.nordakademie.nakjava.client.shared;

import java.rmi.Remote;

public interface PlayerControlListener extends Remote {
	public void remoteClose();

	public void error(String text);
}
