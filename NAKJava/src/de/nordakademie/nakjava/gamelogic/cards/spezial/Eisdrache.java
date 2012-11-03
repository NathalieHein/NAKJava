package de.nordakademie.nakjava.gamelogic.cards.spezial;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Eisdrache",
		type = CardType.SPEZIAL,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 25), @Cost(ressource = Monster.class,
				amount = 6) },
		damageEffects = { @DamageEffect(count = 20,
				target = Target.OPPONENT) },
		artifactEffects = { @ArtifactEffect(artifact = Zauberlabor.class,
				target = Target.SELF,
				count = -1), @ArtifactEffect(artifact = Ziegel.class,
				target = Target.SELF,
				count = -10) })
public class Eisdrache extends AbstractCard {

}
