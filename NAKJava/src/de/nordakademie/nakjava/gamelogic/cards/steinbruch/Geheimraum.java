package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;

@Card(name = "Geheimraum",
		type = CardType.STEINBRUCH,
		costs = { @Cost(amount = 8,
				ressource = Ziegel.class) },
		artifactEffects = { @ArtifactEffect(artifact = Zauberlabor.class,
				count = 1,
				target = Target.SELF) },
		additionalDescription = "Noch eine Karte spielen.")
public class Geheimraum extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		PlayerState self = states.get(Target.SELF);
		self.setState(State.PLAYCARDSTATE);
	}

}
