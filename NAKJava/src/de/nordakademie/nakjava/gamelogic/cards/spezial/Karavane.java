package de.nordakademie.nakjava.gamelogic.cards.spezial;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Kometenschwerif",
		type = CardType.SPEZIAL,
		costs = { @Cost(ressource = Monster.class,
				amount = 5), @Cost(ressource = Kristalle.class,
				amount = 5) },
		artifactEffects = { @ArtifactEffect(artifact = Zauberlabor.class,
				target = Target.SELF,
				count = 1), @ArtifactEffect(artifact = Verlies.class,
				target = Target.SELF,
				count = 1) })
public class Karavane extends AbstractCard {

}
