package de.nordakademie.nakjava.gamelogic.cards.spezial;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Seelenfresser",
		type = CardType.SPEZIAL,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 7), @Cost(ressource = Monster.class,
				amount = 11) },
		artifactEffects = { @ArtifactEffect(artifact = Monster.class,
				target = Target.OPPONENT,
				count = -10), @ArtifactEffect(artifact = Kristalle.class,
				target = Target.OPPONENT,
				count = -10), @ArtifactEffect(artifact = Ziegel.class,
				target = Target.OPPONENT,
				count = -2) })
public class Seelenfresser extends AbstractCard {

}
