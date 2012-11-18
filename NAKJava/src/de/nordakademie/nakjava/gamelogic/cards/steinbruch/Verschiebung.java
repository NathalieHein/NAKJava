package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Verschiebung",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 17,
				ressource = Ziegel.class) },
		additionalDescription = "Tausche deine Mauer mit der des Gegners.")
public class Verschiebung extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Mauer selfMauer = selfModel.getTupelForClass(Mauer.class);
		Mauer opponentMauer = opponentModel.getTupelForClass(Mauer.class);
		int diff = selfMauer.getCount() - opponentMauer.getCount();
		selfModel.getTupelForClass(Mauer.class).merge(
				ArtifactFactory.createArtifact(Mauer.class, -diff));
		opponentModel.getTupelForClass(Mauer.class).merge(
				ArtifactFactory.createArtifact(Mauer.class, diff));
	}

}
