package de.nordakademie.nakjava.gamelogic.cards.steinbruch;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Steinbruch;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Erdbeben",
		type = CardType.STEINBRUCH,
		artifactEffects = { @ArtifactEffect(artifact = Steinbruch.class,
				target = Target.SELF,
				count = -1), @ArtifactEffect(artifact = Steinbruch.class,
				target = Target.OPPONENT,
				count = -1) })
public class Erdbeben extends AbstractCard {

}
