package de.nordakademie.nakjava.gamelogic.cards;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(
		additionalDescription = "Orkan",
		name = "Ein Orkan bläst über deine Birne. Das ist nur ein Test",
		type = CardType.STEINBRUCH,
		costs = { @Cost(ressource = Ziegel.class, amount = 15) },
		artifactEffects = { @ArtifactEffect(
				artifact = Monster.class,
				count = -15,
				target = Target.SELF) })
public class Orkan extends AbstractCard {

}
