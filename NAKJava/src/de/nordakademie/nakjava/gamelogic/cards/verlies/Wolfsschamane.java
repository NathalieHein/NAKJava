package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Wolfsschamane",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 3) },
		artifactEffects = { @ArtifactEffect(artifact = Verlies.class,
				target = Target.SELF,
				count = 2), @ArtifactEffect(artifact = Turm.class,
				target = Target.SELF,
				count = -7) })
public class Wolfsschamane extends AbstractCard {

}
