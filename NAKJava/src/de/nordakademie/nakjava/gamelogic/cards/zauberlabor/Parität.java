package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Parität",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(amount = 7,
				ressource = Kristalle.class) },
		additionalDescription = "Beide Zauberlabore erhalten die Stufe des aktuell höchsten Zauberlabors im Spiel")
public class Parität extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Zauberlabor selfZauberlabor = selfModel
				.getTupelForClass(Zauberlabor.class);
		Zauberlabor opponentZauberlabor = opponentModel
				.getTupelForClass(Zauberlabor.class);
		if (selfZauberlabor.getCount() > opponentZauberlabor.getCount()) {
			int diff = selfZauberlabor.getCount()
					- opponentZauberlabor.getCount();
			opponentZauberlabor.merge(ArtifactFactory.createArtifact(
					Zauberlabor.class, diff));
		} else {
			int diff = opponentZauberlabor.getCount()
					- selfZauberlabor.getCount();
			selfZauberlabor.merge(ArtifactFactory.createArtifact(
					Zauberlabor.class, diff));
		}
	}

}
