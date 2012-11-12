package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategyInformation;
import de.nordakademie.nakjava.server.internal.model.VisibleField;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;

public class ConfigurationSpecificInformation implements
		StateSpecificInformation {
	// TODO to be implemented
	private Map<String, WinStrategyInformation> winStrategyDescription;
	@VisibleField(targets = { @TargetInState(target = Target.SELF,
			states = { State.CONFIGUREGAME }) })
	// TODO winStrategy is the same for both players, how to handle?
	private String currentlyChosenWinStrategy;
	@VisibleField(targets = { @TargetInState(target = Target.SELF,
			states = { State.CONFIGUREGAME }) })
	private String currentlyChosenCardDeck;

	public ConfigurationSpecificInformation(
			Map<String, WinStrategyInformation> winStrategyDescription,
			String currentlyChosenWinStrategy, String currentlyChosenCardDeck) {
		this.winStrategyDescription = winStrategyDescription;
		this.currentlyChosenWinStrategy = currentlyChosenWinStrategy;
		this.currentlyChosenCardDeck = currentlyChosenCardDeck;
	}

	public Map<String, WinStrategyInformation> getWinStrategyDescription() {
		return winStrategyDescription;
	}

	public String getCurrentlyChosenWinStrategy() {
		return currentlyChosenWinStrategy;
	}

	public String getCurrentlyChosenCardDeck() {
		return currentlyChosenCardDeck;
	}

}
