package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.ArtifactChecker;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.Comparator;

@WinStrategy(artifactCheckers = {
		@ArtifactChecker(
				target = Target.SELF,
				artifacts = { Turm.class },
				comparator = Comparator.GREATER,
				count = 99),
		@ArtifactChecker(
				target = Target.OPPONENT,
				artifacts = { Turm.class },
				comparator = Comparator.SMALLER,
				count = 1) })
public class Turmbau extends AbstractWinStrategy {

}
