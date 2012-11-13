package de.nordakademie.nakjava.server.internal;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControl;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControlImpl;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControl control;
	// TODO name and savedDecks should go into another entity
	private String name = "";
	// TODO savedDecks include StandardDeck
	private List<Deck> savedDecks;

	private static ExecutorService threadPool = Executors.newCachedThreadPool();

	public Player(PlayerControlListener controlListener,
			PlayerStateListener stateListener) {

		try {
			control = new PlayerControlImpl(controlListener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// TODO get saved decks instead when login finished
		savedDecks = new ArrayList<>();
		state = new PlayerState(stateListener);
	}

	public PlayerState getState() {
		return state;
	}

	public PlayerControl getControl() {
		return control;
	}

	public void triggerChangeEvent() {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				state.triggerChangeEvent();
			}

		});
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				control.triggerChangeEvent();
			}

		});

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Deck> getSavedDecks() {
		return savedDecks;
	}

	public void setSavedDecks(List<Deck> savedDecks) {
		this.savedDecks = savedDecks;
	}

	public void addDeck(String name, Set<String> cards) {
		savedDecks.add(new Deck(name, cards));
	}

	public Deck getDeckWithName(String name) {
		for (Deck deck : savedDecks) {
			if (deck.getName().equals(name)) {
				return deck;
			}
		}
		return null;
	}

}
