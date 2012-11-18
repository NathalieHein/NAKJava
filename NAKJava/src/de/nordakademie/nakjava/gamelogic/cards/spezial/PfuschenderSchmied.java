package de.nordakademie.nakjava.gamelogic.cards.spezial;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Pfuschender Schmied",
		type = CardType.SPEZIAL,
		costs = { @Cost(amount = 2,
				ressource = Kristalle.class), @Cost(amount = 2,
				ressource = Ziegel.class) },
		additionalDescription = "Gegner legt alle Steinbruch-Karten auf den Kartenfriedhof, ziehe und spiele noch eine Karte")
public class PfuschenderSchmied extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		CardSet opponentCards = ((InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel()).getCards();
		List<String> cardsOnHand = new LinkedList<>();
		cardsOnHand.addAll(opponentCards.getCardsOnHand());
		for (String card : cardsOnHand) {
			if (CardLibrary.get().getCardInformationForName(card).getType() == CardType.STEINBRUCH) {
				opponentCards.discardCardFromHand(card);
			}
		}

		PlayerState self = states.get(Target.SELF);
		((InGameSpecificModel) self.getStateSpecificModel()).getCards()
				.drawNCardsFromDeck(1);
		self.setState(State.PLAYCARDSTATE);
	}

}
