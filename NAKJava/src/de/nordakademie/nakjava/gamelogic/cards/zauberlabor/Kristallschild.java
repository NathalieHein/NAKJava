package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Kristallschild",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 12) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				target = Target.SELF,
				count = 8), @ArtifactEffect(artifact = Mauer.class,
				target = Target.SELF,
				count = 3) })
public class Kristallschild extends AbstractCard {

}
