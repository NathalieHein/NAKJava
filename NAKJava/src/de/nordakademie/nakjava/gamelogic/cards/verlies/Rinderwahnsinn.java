package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Rinderwahnsinn",
		type = CardType.VERLIES,
		artifactEffects = { @ArtifactEffect(artifact = Monster.class,
				target = Target.SELF,
				count = -6), @ArtifactEffect(artifact = Monster.class,
				target = Target.OPPONENT,
				count = -6) })
public class Rinderwahnsinn extends AbstractCard {

}
