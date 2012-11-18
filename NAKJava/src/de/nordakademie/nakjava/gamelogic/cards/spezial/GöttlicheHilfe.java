package de.nordakademie.nakjava.gamelogic.cards.spezial;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ressource;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Göttliche Hilfe",
		type = CardType.SPEZIAL,
		artifactEffects = { @ArtifactEffect(artifact = Ressource.class,
				count = 5,
				target = Target.SELF) })
public class GöttlicheHilfe extends AbstractCard {

}
