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

@Card(name = "Speerträger",
		type = CardType.VERLIES,
		costs = { @Cost(amount = 2,
				ressource = Monster.class) },
		additionalDescription = "Wenn Mauer > gegnerische Mauer dann 3 Schaden sonst 2 Schaden.")
public class Speerträger extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		Mauer opponentMauer = opponentModel.getTupelForClass(Mauer.class);
		Mauer selfMauer = selfModel.getTupelForClass(Mauer.class);
		if (selfMauer.getCount() > opponentMauer.getCount()) {
			performOneDamage(states, 3, Target.OPPONENT);
		} else {
			performOneDamage(states, 2, Target.OPPONENT);
		}
	}

}
