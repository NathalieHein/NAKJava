package de.nordakademie.nakjava.gamelogic.cards.verlies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Elfische Bogenschützen",
		type = CardType.VERLIES,
		costs = { @Cost(amount = 10,
				ressource = Monster.class) },
		additionalDescription = "Wenn Mauer>gegnerische Mauer dann 6 Turmschaden sonst 6 Schaden")
public class ElfischeBogenschützen extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Mauer selfMauer = selfModel.getTupelForClass(Mauer.class);
		Mauer opponentMauer = opponentModel.getTupelForClass(Mauer.class);
		if (selfMauer.getCount() > opponentMauer.getCount()) {
			opponentModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, -6));
		} else {
			performOneDamage(states, 6, Target.OPPONENT);
		}
	}

}
