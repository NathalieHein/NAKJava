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

@Card(name = "Grundstein",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 3,
				ressource = Ziegel.class) },
		additionalDescription = "Wenn Mauer=0 dann +6 Mauer sonst +3 Mauer.")
public class Grundstein extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		Mauer selfMauer = selfModel.getTupelForClass(Mauer.class);
		if (selfMauer.getCount() == 0) {
			selfModel.getTupelForClass(Mauer.class).merge(
					ArtifactFactory.createArtifact(Mauer.class, 6));
		} else {
			selfModel.getTupelForClass(Mauer.class).merge(
					ArtifactFactory.createArtifact(Mauer.class, 3));

		}
	}

}
