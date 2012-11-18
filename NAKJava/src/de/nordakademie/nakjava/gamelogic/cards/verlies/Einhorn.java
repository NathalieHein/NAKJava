package de.nordakademie.nakjava.gamelogic.cards.verlies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Einhorn",
		type = CardType.VERLIES,
		costs = { @Cost(amount = 9,
				ressource = Monster.class) },
		additionalDescription = "Wenn Zauberlabor>gegnerisches Zauberlabor dann 12 Schaden sonst 8 Schaden.")
public class Einhorn extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Zauberlabor selfZauberlabor = selfModel
				.getTupelForClass(Zauberlabor.class);
		Zauberlabor opponentZauberlabor = opponentModel
				.getTupelForClass(Zauberlabor.class);
		if (selfZauberlabor.getCount() > opponentZauberlabor.getCount()) {
			performOneDamage(states, 12, Target.OPPONENT);
		} else {
			performOneDamage(states, 8, Target.OPPONENT);
		}
	}

}
