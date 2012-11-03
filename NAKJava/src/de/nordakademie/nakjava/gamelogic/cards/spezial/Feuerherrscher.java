package de.nordakademie.nakjava.gamelogic.cards.spezial;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Feuerherrscher",
		type = CardType.SPEZIAL,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 36), @Cost(ressource = Ziegel.class,
				amount = 30) },
		damageEffects = { @DamageEffect(target = Target.OPPONENT,
				count = 40) },
		artifactEffects = { @ArtifactEffect(artifact = Zauberlabor.class,
				target = Target.OPPONENT,
				count = -1) })
public class Feuerherrscher extends AbstractCard {

}
