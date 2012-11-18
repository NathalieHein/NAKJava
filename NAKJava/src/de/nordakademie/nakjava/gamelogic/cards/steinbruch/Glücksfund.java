package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Glücksfund",
		type = CardType.STEINBRUCH,
		artifactEffects = { @ArtifactEffect(artifact = Ziegel.class,
				count = 2,
				target = Target.SELF),
				@ArtifactEffect(artifact = Kristalle.class,
						count = 2,
						target = Target.SELF) },
		additionalDescription = "Noch eine Karte ziehen und spielen.")
public class Glücksfund extends AbstractCard {
	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		PlayerState self = states.get(Target.SELF);
		((InGameSpecificModel) self.getStateSpecificModel()).getCards()
				.drawNCardsFromDeck(1);
		self.setState(State.PLAYCARDSTATE);
	}
}
