package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Baukoloss",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(amount = 5,
				ressource = Kristalle.class) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				count = 3,
				target = Target.SELF) },
		canDrop = false)
public class Baukoloss extends AbstractCard {

}
