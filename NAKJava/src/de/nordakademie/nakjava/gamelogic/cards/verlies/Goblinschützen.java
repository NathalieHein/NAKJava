package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Goblinschützen",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 4) },
		damageEffects = { @DamageEffect(target = Target.SELF,
				count = 1) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				target = Target.OPPONENT,
				count = -3) })
public class Goblinschützen extends AbstractCard {

}
