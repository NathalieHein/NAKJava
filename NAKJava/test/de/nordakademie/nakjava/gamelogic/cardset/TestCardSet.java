package de.nordakademie.nakjava.gamelogic.cardset;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;

public class TestCardSet {
	private CardSet cardSet;
	Set<String> setOfCardNames;

	@Before
	public void setup() {
		setOfCardNames = new HashSet<>();
		setOfCardNames.add("Zyklop");
		setOfCardNames.add("Zwerge");
		setOfCardNames.add("Statue");
		setOfCardNames.add("Vampir");
		setOfCardNames.add("Vollmond");
		setOfCardNames.add("Quarz");
		setOfCardNames.add("Prisma");
	}

	@Test
	public void testEmptyConstructor1() {
		cardSet = new CardSet();
		Assert.assertNotNull(cardSet.getCardsOnHand());
		Assert.assertEquals(true, cardSet.getCardSetSize() == 0);
	}

	@Test
	public void testSetConstructor() {
		cardSet = new CardSet(setOfCardNames);
		Assert.assertNotNull(cardSet.getCardsOnHand());
		Assert.assertEquals(true,
				cardSet.getCardSetSize() == setOfCardNames.size());
		Assert.assertEquals(true, cardSet.getNumberOfCardsOnHand() == 0);
	}

	@Test
	public void testAddOneCard() {
		cardSet = new CardSet(setOfCardNames);
		cardSet.addOneCard("Orkan");
		Assert.assertEquals(true,
				cardSet.getCardSetSize() == setOfCardNames.size() + 1);
	}

	@Test
	public void testShuffle() {
		cardSet = new CardSet(setOfCardNames);
		cardSet.drawNCardsFromDeck(cardSet.getCardSetSize());
		Assert.assertNull(cardSet.drawCardFromDeck());
		cardSet.discardRandomCardFromHand();
		Assert.assertNotNull(cardSet.drawCardFromDeck());

	}

	@Test
	public void testDrawCard() {
		cardSet = new CardSet(setOfCardNames);
		cardSet.drawUntilNCardsOnHand(5);
		int drawUntil = cardSet.getNumberOfCardsOnHand();
		cardSet.discardAllCardsFromHand();
		cardSet.drawNCardsFromDeck(5);
		int drawNumber = cardSet.getNumberOfCardsOnHand();
		Assert.assertEquals(drawUntil, drawNumber);
	}
}
