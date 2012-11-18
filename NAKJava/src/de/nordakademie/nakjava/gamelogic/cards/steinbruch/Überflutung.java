package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Überflutung",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 6,
				ressource = Ziegel.class) },
		additionalDescription = "Spieler mit niedrigster Mauer -1 Verlies und 2 Turmschaden.")
public class Überflutung extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Mauer selfMauer = selfModel.getTupelForClass(Mauer.class);
		Mauer opponentMauer = opponentModel.getTupelForClass(Mauer.class);
		if (selfMauer.getCount() < opponentMauer.getCount()) {
			selfModel.getTupelForClass(Verlies.class).merge(
					ArtifactFactory.createArtifact(Verlies.class, -1));
			selfModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, -2));
		} else if (selfMauer.getCount() > opponentMauer.getCount()) {
			opponentModel.getTupelForClass(Verlies.class).merge(
					ArtifactFactory.createArtifact(Verlies.class, -1));
			opponentModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, -2));
		}
	}

}
