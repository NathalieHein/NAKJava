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

@Card(name = "Gnome",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 2) },
		damageEffects = { @DamageEffect(target = Target.OPPONENT,
				count = 3) },
		artifactEffects = { @ArtifactEffect(artifact = Kristalle.class,
				target = Target.SELF,
				count = 1) })
public class Gnome extends AbstractCard {

}
