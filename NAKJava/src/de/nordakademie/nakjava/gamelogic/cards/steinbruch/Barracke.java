package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Barracke",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 10,
				ressource = Ziegel.class) },
		artifactEffects = { @ArtifactEffect(artifact = Monster.class,
				count = 6,
				target = Target.SELF), @ArtifactEffect(artifact = Mauer.class,
				count = 6,
				target = Target.SELF) },
		additionalDescription = "Wenn gegnerisches Verlies > eigenes Verlies +1 Verlies.")
public class Barracke extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		InGameSpecificModel selfModel = (InGameSpecificModel) states.get(
				Target.SELF).getStateSpecificModel();
		InGameSpecificModel opponentModel = (InGameSpecificModel) states.get(
				Target.OPPONENT).getStateSpecificModel();
		Verlies selfVerlies = selfModel.getTupelForClass(Verlies.class);
		Verlies opponentVerlies = opponentModel.getTupelForClass(Verlies.class);
		if (selfVerlies.getCount() < opponentVerlies.getCount()) {
			selfModel.getTupelForClass(Verlies.class).merge(
					ArtifactFactory.createArtifact(Verlies.class, 1));
		}
	}

}
