package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Perle der Weisheit",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 9) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				target = Target.SELF,
				count = 5), @ArtifactEffect(artifact = Zauberlabor.class,
				target = Target.SELF,
				count = 1) })
public class PerleDerWeisheit extends AbstractCard {

}
