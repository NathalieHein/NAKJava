package de.nordakademie.nakjava.gamelogic.cards.spezial;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.DamageEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Vulkanausbruch",
		type = CardType.SPEZIAL,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 8), @Cost(ressource = Ziegel.class,
				amount = 8) },
		damageEffects = { @DamageEffect(target = Target.SELF,
				count = 20), @DamageEffect(target = Target.SELF,
				count = 20) })
public class Vulkanausbruch extends AbstractCard {

}
