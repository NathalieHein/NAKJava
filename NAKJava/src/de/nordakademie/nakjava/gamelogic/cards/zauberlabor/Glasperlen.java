package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Glasperlen",
		type = CardType.ZAUBERLABOR,
		additionalDescription = "Wenn gegnerischer Turm > eigener Turmdann +2 Turm sonst +1 Turm")
public class Glasperlen extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Turm selfTurm = selfModel.getTupelForClass(Turm.class);
		Turm opponentTurm = opponentModel.getTupelForClass(Turm.class);
		if (selfTurm.getCount() < opponentTurm.getCount()) {
			selfModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, 2));
		} else {
			selfModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, 1));
		}
	}

}
