package de.nordakademie.nakjava.gamelogic.statemachine;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class TestStateMachine {

	Model model;
	PlayerState ps1;
	PlayerState ps2;
	Deck deck;

	@Before
	public void setup() {
		ClasspathScanner.lookupAnnotatedScanners();
		ps1 = new PlayerState();
		ps2 = new PlayerState();
		model = new Model(ps1);
		model.addPlayerState(ps2);

		Set<String> setOfCardNames = new HashSet<>();
		setOfCardNames.add("Zyklop");
		setOfCardNames.add("Zwerge");
		setOfCardNames.add("Statue");
		setOfCardNames.add("Vampir");
		setOfCardNames.add("Vollmond");
		setOfCardNames.add("Quarz");
		setOfCardNames.add("Prisma");
		setOfCardNames.add("Statue");
		setOfCardNames.add("Vampir");
		setOfCardNames.add("Vollmond");
		setOfCardNames.add("Quarz");
		setOfCardNames.add("Prisma");
		setOfCardNames.add("Zyklop");
		setOfCardNames.add("Zwerge");
		setOfCardNames.add("Statue");
		setOfCardNames.add("Vampir");
		setOfCardNames.add("Vollmond");
		setOfCardNames.add("Quarz");
		setOfCardNames.add("Prisma");
		setOfCardNames.add("Statue");
		setOfCardNames.add("Vampir");
		setOfCardNames.add("Vollmond");
		setOfCardNames.add("Quarz");
		setOfCardNames.add("Prisma");
		deck = new Deck("aDeck", setOfCardNames);
	}

	@Test
	public void testExistence() {
		Assert.assertNotNull(StateMachine.getInstance());
	}

	@Test
	public void testGameBegin() {
		model.setStrategy("Turmbau");
		ps1.setState(State.CONFIGUREGAME);
		if (ps1 != model.getSelf()) {
			model.changeSelfAndOpponent();
		}
		ps2.setState(State.CONFIGUREGAME);
		ps1.setStateSpecificModel(new ConfigureGameSpecificModel(deck));
		ps2.setStateSpecificModel(new ConfigureGameSpecificModel(deck));

		StateMachine.getInstance().run(model);
		Assert.assertEquals(ps1.getState(), State.READYTOSTARTSTATE);
		Assert.assertEquals(ps2.getState(), State.CONFIGUREGAME);

		model.changeSelfAndOpponent();
		StateMachine.getInstance().run(model);
		InGameSpecificModel specModel = (InGameSpecificModel) ps1
				.getStateSpecificModel();
		Assert.assertEquals(specModel.getCards().getNumberOfCardsOnHand(), 6);
		Assert.assertEquals(ps1.getState(), State.PLAYCARDSTATE);
		Assert.assertEquals(ps2.getState(), State.STOP);
	}

	@Test
	public void testAdjustCardHandNotEnoughCards() {
		testGameBegin();
		InGameSpecificModel specModel = (InGameSpecificModel) ps1
				.getStateSpecificModel();
		specModel.getCards().discardAllCardsFromHand();
		StateMachine.getInstance().run(model);
		Assert.assertEquals(ps1.getState(), State.STOP);
		Assert.assertEquals(ps2.getState(), State.PLAYCARDSTATE);
		Assert.assertEquals(specModel.getCards().getNumberOfCardsOnHand(), 6);
	}

	@Test
	public void testAdjustCardHandToMuch() {
		testGameBegin();
		InGameSpecificModel specModel = (InGameSpecificModel) ps1
				.getStateSpecificModel();
		specModel.getCards().drawCardFromDeck();
		StateMachine.getInstance().run(model);
		Assert.assertEquals(ps1.getState(), State.ADJUSTCARDHANDSTATE);
		Assert.assertEquals(ps2.getState(), State.STOP);
	}

}
