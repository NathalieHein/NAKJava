package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.DeckTransformator;
import de.nordakademie.nakjava.server.internal.model.transformator.WinStrategyTransformator;
import de.nordakademie.nakjava.server.persistence.Deck;

public class ConfigureGameSpecificModel implements StateSpecificModel {
	@VisibleField(targets = { @TargetInState(states = { State.CONFIGUREGAME }, target = Target.SELF) }, transformer = DeckTransformator.class)
	private Deck chosenDeck;
	@VisibleField(targets = { @TargetInState(states = { State.CONFIGUREGAME }, target = Target.SELF) }, transformer = WinStrategyTransformator.class)
	private String winStrategy;

	public ConfigureGameSpecificModel(Deck chosenDeck, String winStrategy) {
		this.chosenDeck = chosenDeck;
		this.winStrategy = winStrategy;
	}

	public Deck getChosenDeck() {
		return chosenDeck;
	}

	public void setChosenDeck(Deck chosenDeck) {
		this.chosenDeck = chosenDeck;
	}

	public String getWinStrategy() {
		return winStrategy;
	}

	public void setWinStrategy(String winStrategy) {
		this.winStrategy = winStrategy;
	}

}
