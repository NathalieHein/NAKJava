package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Steinbruch;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Recycling",
		type = CardType.STEINBRUCH,
		costs = { @Cost(ressource = Ziegel.class,
				amount = 5) },
		artifactEffects = { @ArtifactEffect(artifact = Mauer.class,
				target = Target.SELF,
				count = -15), @ArtifactEffect(artifact = Steinbruch.class,
				target = Target.SELF,
				count = 2) })
public class Recycling extends AbstractCard {

}
