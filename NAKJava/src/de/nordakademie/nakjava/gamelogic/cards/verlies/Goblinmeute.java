package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Goblinmeute",
		type = CardType.VERLIES,
		costs = { @Cost(ressource = Monster.class,
				amount = 3) },
		damageEffects = { @DamageEffect(target = Target.OPPONENT,
				count = 6), @DamageEffect(target = Target.SELF,
				count = 3) })
public class Goblinmeute extends AbstractCard {

}
