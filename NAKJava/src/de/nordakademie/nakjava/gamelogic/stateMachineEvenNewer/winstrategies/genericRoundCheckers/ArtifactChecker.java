package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public @interface ArtifactChecker {
	Target target();

	Class<? extends Artifact>[] artifacts();

	Comparator comparator();

	int count();

	boolean operator() default AND;

	public static final boolean AND = true;
	public static final boolean OR = false;
}
