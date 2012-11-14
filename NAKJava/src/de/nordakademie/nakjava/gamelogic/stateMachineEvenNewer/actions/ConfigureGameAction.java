package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.persistence.Deck;

public class ConfigureGameAction implements StateAction {

	@Override
	public void perform(Model model) {
		// TODO looks ugly: utility-methods in WinStrategies and CardLibrary??
		ConfigureGameSpecificModel configureModel = new ConfigureGameSpecificModel(
				new Deck("StandardDeck", CardLibrary.get().getCards().keySet()));
		model.getSelf().setStateSpecificModel(configureModel);

	}
}
