package de.nordakademie.nakjava.gamelogic.cards.spezial;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Baumgeist",
		type = CardType.SPEZIAL,
		costs = { @Cost(amount = 9,
				ressource = Monster.class), @Cost(amount = 7,
				ressource = Ziegel.class) },
		additionalDescription = "Wenn Verlies > gegnerische Mauer dann 11 Turmschaden.")
public class Baumgeist extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Verlies selfVerlies = selfModel.getTupelForClass(Verlies.class);
		Mauer opponentMauer = opponentModel.getTupelForClass(Mauer.class);
		if (selfVerlies.getCount() > opponentMauer.getCount()) {
			opponentModel.getTupelForClass(Turm.class).merge(
					ArtifactFactory.createArtifact(Turm.class, -11));
		}
	}

}
