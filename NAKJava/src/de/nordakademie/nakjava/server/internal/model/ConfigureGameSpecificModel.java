package de.nordakademie.nakjava.server.internal.model;

import java.util.Set;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.WinStrategyTransformator;

public class ConfigureGameSpecificModel implements StateSpecificModel {
	@VisibleField(targets = { @TargetInState(states = { State.CONFIGUREGAME }, target = Target.SELF) })
	private Set<String> chosenDeck;
	@VisibleField(targets = { @TargetInState(states = { State.CONFIGUREGAME }, target = Target.SELF) }, transformer = WinStrategyTransformator.class)
	private String winStrategy;

	public ConfigureGameSpecificModel(Set<String> chosenDeck, String winStrategy) {
		this.chosenDeck = chosenDeck;
		this.winStrategy = winStrategy;
	}

	public Set<String> getChosenDeck() {
		return chosenDeck;
	}

	public void setChosenDeck(Set<String> chosenDeck) {
		this.chosenDeck = chosenDeck;
	}

	public String getWinStrategy() {
		return winStrategy;
	}

	public void setWinStrategy(String winStrategy) {
		this.winStrategy = winStrategy;
	}

}
