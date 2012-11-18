package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.persistence.Deck;

/**
 * initializes and sets the configurationModel when player gets to State
 * ConfigureGame
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class ConfigureGameAction implements StateAction {

	@Override
	public void perform(Model model) {
		ConfigureGameSpecificModel configureModel = new ConfigureGameSpecificModel(
				new Deck("StandardDeck", CardLibrary.get().getCards().keySet()));
		model.getSelf().setStateSpecificModel(configureModel);
		model.setLastPlayedCard(null);

	}
}
