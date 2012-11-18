package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Blitz und Donner",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(amount = 11,
				ressource = Kristalle.class) },
		additionalDescription = "Wenn Turm>gegnerischer Turm dann 8 Turmschaden sonst 8 Schaden")
public class BlitzUndDonner extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Turm selfTurm = selfModel.getTupelForClass(Turm.class);
		Turm opponentTurm = opponentModel.getTupelForClass(Turm.class);
		if (selfTurm.getCount() > opponentTurm.getCount()) {
			opponentModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, -8));
		} else {
			performOneDamage(states, 8, Target.OPPONENT);
		}
	}
}
