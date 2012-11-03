package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Lavajuwel",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 17) },
		damageEffects = { @DamageEffect(target = Target.OPPONENT,
				count = 6) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				target = Target.SELF,
				count = 12) })
public class Lavajuwel extends AbstractCard {

}
