package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Kobold",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 5) },
		damageEffects = { @DamageEffect(target = Target.OPPONENT,
				count = 6) },
		artifactEffects = { @ArtifactEffect(artifact = Ziegel.class,
				target = Target.SELF,
				count = -5), @ArtifactEffect(artifact = Kristalle.class,
				target = Target.SELF,
				count = -5), @ArtifactEffect(artifact = Monster.class,
				target = Target.SELF,
				count = -5), @ArtifactEffect(artifact = Ziegel.class,
				target = Target.OPPONENT,
				count = -5), @ArtifactEffect(artifact = Kristalle.class,
				target = Target.OPPONENT,
				count = -5), @ArtifactEffect(artifact = Kristalle.class,
				target = Target.OPPONENT,
				count = -5) })
public class Kobold extends AbstractCard {

}
