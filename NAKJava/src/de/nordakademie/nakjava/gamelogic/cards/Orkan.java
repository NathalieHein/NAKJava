package de.nordakademie.nakjava.gamelogic.cards;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.ressources.Ressource;

@Card(additionalDescription = "Orkan", name = "Ein Orkan bläst über deine Birne. Das ist nur ein Test", type = CardType.STEINBRUCH, costs = { @Cost(ressource = Ressource.ZIEGEL, amount = 15) })
public class Orkan extends AbstractCard {

}
