package de.nordakademie.nakjava.gamelogic.cardstest;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "TestKarte", type = CardType.SPEZIAL, additionalDescription = "zusätzlicherTest", costs = {
		@Cost(ressource = Ziegel.class, amount = 15),
		@Cost(ressource = Kristalle.class, amount = 30) }, damageEffects = { @DamageEffect(count = 5, target = Target.SELF) }, artifactEffects = { @ArtifactEffect(artifact = Monster.class, count = -15, target = Target.SELF) })
public class TestCard extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		((InGameSpecificModel) states.get(Target.SELF).getStateSpecificModel())
				.getTupelForClass(Kristalle.class).setCount(10);
		super.performAction(states);
	}

}
