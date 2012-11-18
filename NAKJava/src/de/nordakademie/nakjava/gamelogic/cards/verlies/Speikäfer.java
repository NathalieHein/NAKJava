package de.nordakademie.nakjava.gamelogic.cards.verlies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Speikäfer",
		type = CardType.VERLIES,
		costs = @Cost(amount = 8,
				ressource = Monster.class),
		additionalDescription = "Wenn gegnerische Mauer = 0 dann 10 Schaden sonst 6 Schaden.")
public class Speikäfer extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Mauer opponentMauer = opponentModel.getTupelForClass(Mauer.class);
		if (opponentMauer.getCount() == 0) {
			performOneDamage(states, 10, Target.OPPONENT);
		} else {
			performOneDamage(states, 6, Target.OPPONENT);
		}
	}

}
