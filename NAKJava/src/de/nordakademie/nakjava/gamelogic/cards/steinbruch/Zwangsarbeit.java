package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Zwangsarbeit",
		type = CardType.STEINBRUCH,
		costs = { @Cost(ressource = Ziegel.class,
				amount = 7) },
		artifactEffects = { @ArtifactEffect(artifact = Monster.class,
				target = Target.SELF,
				count = -5), @ArtifactEffect(artifact = Mauer.class,
				target = Target.SELF,
				count = 9) })
public class Zwangsarbeit extends AbstractCard {

}
