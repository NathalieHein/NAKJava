package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ressource;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Feuerwerk",
		type = CardType.ZAUBERLABOR,
		artifactEffects = { @ArtifactEffect(artifact = Ressource.class,
				count = 7,
				target = Target.SELF), @ArtifactEffect(artifact = Turm.class,
				count = -7,
				target = Target.SELF) })
public class Feuerwerk extends AbstractCard {
}
