package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Steinbruch;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Hauptader",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 4,
				ressource = Ziegel.class) },
		additionalDescription = "Wenn gegnerischer Steinbruch > eigener Steinbruch dann +2 Steinbruch sonst +1 Steinbruch")
public class Hauptader extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Steinbruch selfSteinbruch = selfModel
				.getTupelForClass(Steinbruch.class);
		Steinbruch opponentSteinbruch = opponentModel
				.getTupelForClass(Steinbruch.class);
		if (selfSteinbruch.getCount() < opponentSteinbruch.getCount()) {
			selfModel.getTupelForClass(Steinbruch.class).merge(
					ArtifactFactory.createArtifact(Steinbruch.class, 2));
		} else {
			selfModel.getTupelForClass(Steinbruch.class).merge(
					ArtifactFactory.createArtifact(Steinbruch.class, 1));
		}
	}

}
