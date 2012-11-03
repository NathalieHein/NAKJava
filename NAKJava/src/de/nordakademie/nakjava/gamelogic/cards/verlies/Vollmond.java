package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Vollmond",
		type = CardType.VERLIES,
		artifactEffects = { @ArtifactEffect(artifact = Verlies.class,
				target = Target.SELF,
				count = 1), @ArtifactEffect(artifact = Verlies.class,
				target = Target.OPPONENT,
				count = 1), @ArtifactEffect(artifact = Monster.class,
				target = Target.SELF,
				count = 3) })
public class Vollmond extends AbstractCard {

}
