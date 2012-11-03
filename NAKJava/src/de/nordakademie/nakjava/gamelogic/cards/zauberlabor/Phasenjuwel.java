package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

@Card(name = "Phasenjuwel",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(ressource = Kristalle.class,
				amount = 18) },
		artifactEffects = { @ArtifactEffect(artifact = Turm.class,
				target = Target.SELF,
				count = 13), @ArtifactEffect(artifact = Ziegel.class,
				target = Target.SELF,
				count = 6), @ArtifactEffect(artifact = Monster.class,
				target = Target.SELF,
				count = 6) })
public class Phasenjuwel extends AbstractCard {

}
