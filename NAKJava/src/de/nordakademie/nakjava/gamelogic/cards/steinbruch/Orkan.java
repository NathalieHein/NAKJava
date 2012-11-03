package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Orkan",
		type = CardType.STEINBRUCH,
		costs = { @Cost(ressource = Ziegel.class,
				amount = 20) },
		artifactEffects = { @ArtifactEffect(artifact = Mauer.class,
				target = Target.OPPONENT,
				count = -10), @ArtifactEffect(artifact = Turm.class,
				target = Target.OPPONENT,
				count = -10) })
public class Orkan extends AbstractCard {

}
