package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.DeckTransformator;
import de.nordakademie.nakjava.server.persistence.Deck;

/**
 * SpecificModel for one Player that contains data for the state ConfigurGame
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class ConfigureGameSpecificModel implements StateSpecificModel {
	@VisibleField(targets = { @TargetInState(states = { State.CONFIGUREGAME },
			target = Target.SELF) },
			transformer = DeckTransformator.class)
	private Deck chosenDeck;

	public ConfigureGameSpecificModel(Deck chosenDeck) {
		this.chosenDeck = chosenDeck;
	}

	public Deck getChosenDeck() {
		return chosenDeck;
	}

	public void setChosenDeck(Deck chosenDeck) {
		this.chosenDeck = chosenDeck;
	}

}
