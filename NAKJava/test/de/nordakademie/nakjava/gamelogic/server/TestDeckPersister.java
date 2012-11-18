package de.nordakademie.nakjava.gamelogic.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.persistence.DeckPersister;

public class TestDeckPersister {

	@Test
	public void testPersistDeck() {
		PlayerState state = new PlayerState();
		state.setName("test123");

		Set<String> cards = new HashSet<>();
		Deck deck = new Deck("TestDeck", cards);

		List<Deck> decks = new LinkedList<>();
		decks.add(deck);
		state.setSavedDecks(decks);

		DeckPersister.saveDecks(state);

		List<Deck> loadedDecks = DeckPersister.getDecks(state);

		Assert.assertEquals(loadedDecks.size(), decks.size());
		Assert.assertTrue(loadedDecks.get(0).getCards().equals(cards));
	}
}
