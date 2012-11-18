package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Freundliche Umgebung",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 1,
				ressource = Ziegel.class) },
		artifactEffects = { @ArtifactEffect(artifact = Mauer.class,
				count = 1,
				target = Target.SELF) },
		additionalDescription = "Noch eine Karte ziehen und spielen.")
public class FreundlicheUmgebung extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		PlayerState self = states.get(Target.SELF);
		((InGameSpecificModel) self.getStateSpecificModel()).getCards()
				.drawNCardsFromDeck(1);
		self.setState(State.PLAYCARDSTATE);
	}

}
