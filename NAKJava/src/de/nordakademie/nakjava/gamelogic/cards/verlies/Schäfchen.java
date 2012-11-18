package de.nordakademie.nakjava.gamelogic.cards.verlies;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Schäfchen",
		type = CardType.VERLIES,
		costs = { @Cost(amount = 6,
				ressource = Monster.class) },
		damageEffects = { @DamageEffect(count = 1,
				target = Target.OPPONENT) },
		canDrop = false)
public class Schäfchen extends AbstractCard {

}
