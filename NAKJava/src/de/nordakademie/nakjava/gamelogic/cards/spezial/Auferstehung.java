package de.nordakademie.nakjava.gamelogic.cards.spezial;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ressource;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Auferstehung",
		type = CardType.SPEZIAL,
		costs = { @Cost(amount = 6,
				ressource = Ressource.class) },
		additionalDescription = "Turm +30%.")
public class Auferstehung extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		Turm selfTurm = selfModel.getTupelForClass(Turm.class);
		int additionForTurm = (int) (selfTurm.getCount() * 0.3);
		selfModel.getTupelForClass(Turm.class).merge(
				ArtifactFactory.createArtifact(Turm.class, additionForTurm));
	}
}
