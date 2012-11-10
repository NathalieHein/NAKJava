package de.nordakademie.nakjava.server.internal;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControl;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControlImpl;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControl control;
	// TODO name and savedDecks should go into another entity
	private String name = "";
	// TODO savedDecks include StandardDeck
	private Map<String, Set<String>> savedDecks;

	public Player(PlayerControlListener controlListener,
			PlayerStateListener stateListener) {

		try {
			control = new PlayerControlImpl(controlListener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// TODO get saved decks instead when login finished
		savedDecks = new HashMap<>();
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

	public Map<String, Set<String>> getSavedDecks() {
		return savedDecks;
	}

	public void setSavedDecks(Map<String, Set<String>> savedDecks) {
		this.savedDecks = savedDecks;
	}

	public void addDeck(String name, Set<String> cards) {
		savedDecks.put(name, cards);
	}

}
