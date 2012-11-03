package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Sukkubus",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 14) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				target = Target.OPPONENT,
				count = -5), @ArtifactEffect(artifact = Monster.class,
				target = Target.OPPONENT,
				count = -8) })
public class Sukkubus extends AbstractCard {

}
