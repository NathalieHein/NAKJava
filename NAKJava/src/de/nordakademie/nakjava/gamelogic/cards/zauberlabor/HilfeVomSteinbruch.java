package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Hilfe vom Steinbruch",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 4) },
		artifactEffects = { @ArtifactEffect(artifact = Ziegel.class,
				target = Target.SELF,
				count = -10), @ArtifactEffect(artifact = Turm.class,
				target = Target.SELF,
				count = 7) })
public class HilfeVomSteinbruch extends AbstractCard {

}
