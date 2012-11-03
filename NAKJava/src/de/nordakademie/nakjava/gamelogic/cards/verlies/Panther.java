package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Panther",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 15) },
		damageEffects = { @DamageEffect(target = Target.OPPONENT,
				count = 11) },
		artifactEffects = { @ArtifactEffect(artifact = Kristalle.class,
				target = Target.OPPONENT,
				count = -5) })
public class Panther extends AbstractCard {

}
