package de.nordakademie.nakjava.gamelogic.cards.verlies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Fee",
		type = CardType.VERLIES,
		costs = { @Cost(amount = 1,
				ressource = Monster.class) },
		damageEffects = { @DamageEffect(count = 2,
				target = Target.OPPONENT) },
		additionalDescription = "Ziehe und spiele noch eine Karte.")
public class Fee extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		PlayerState self = states.get(Target.SELF);
		((InGameSpecificModel) self.getStateSpecificModel()).getCards()
				.drawNCardsFromDeck(1);
		self.setState(State.PLAYCARDSTATE);
	}

}
