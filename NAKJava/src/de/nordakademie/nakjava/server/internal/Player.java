package de.nordakademie.nakjava.server.internal;

import java.rmi.RemoteException;
import java.util.Map;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControl;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControlImpl;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControl control;
	// TODO name and savedDecks should go into another entity
	private String name = "";
	// TODO savedDecks include StandardDeck
	private Map<String, CardSet> savedDecks;

	public Player(PlayerControlListener controlListener,
			PlayerStateListener stateListener) {

		try {
			control = new PlayerControlImpl(controlListener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		state = new PlayerState(stateListener);
	}

	public PlayerState getState() {
		return state;
	}

	public PlayerControl getControl() {
		return control;
	}

	public void triggerChangeEvent() {
		// TODO is it here where Exceptions occur??
		state.triggerChangeEvent();
		control.triggerChangeEvent();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, CardSet> getSavedDecks() {
		return savedDecks;
	}

	public void setSavedDecks(Map<String, CardSet> savedDecks) {
		this.savedDecks = savedDecks;
	}

}
