package de.nordakademie.nakjava.client.bot.transformers;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.Comparator;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.impl.ArtifactChecker;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

@CheckTransformer(transformTarget = ArtifactChecker.class)
public class ArtifactCheckTransformer implements WinCheckTransformer {

	@Override
	public WinCheckMeasurement transform(WinCheck check) {
		final ArtifactChecker checker = (ArtifactChecker) check;

		return new WinCheckMeasurement() {

			@Override
			public double measure(Map<Target, PlayerModel> model) {
				PlayerModel pModel = model.get(checker.getTarget());
				double result = 0;

				if (checker.getOperator() == ArtifactChecker.AND) {

					int count = 0;

					for (Class<? extends Artifact> artifact : checker
							.getArtifacts()) {
						Artifact currentArtifact = pModel
								.getArtifactForClass(artifact);

						result += checkArtifact(currentArtifact.getCount(),
								checker.getComparator(), checker.getCount());
						count++;
					}

					result /= count;
				} else {

					for (Class<? extends Artifact> artifact : checker
							.getArtifacts()) {
						Artifact currentArtifact = pModel
								.getArtifactForClass(artifact);

						double tempResult = checkArtifact(
								currentArtifact.getCount(),
								checker.getComparator(), checker.getCount());
						if (tempResult > result) {
							result = tempResult;
						}
					}
				}

				return result;
			}
		};
	}

	private double checkArtifact(int artifactCount, Comparator comparator,
			int targetCount) {
		if (comparator == Comparator.EQUAL) {
			if (artifactCount == targetCount) {
				return 1;
			} else {
				int delta = Math.abs(artifactCount - targetCount);
				return delta / artifactCount;
			}
		}
		if (comparator == Comparator.GREATER) {
			if (artifactCount > targetCount) {
				return 1;
			} else {
				int delta = Math.abs(artifactCount - targetCount);
				return delta / targetCount;
			}
		}
		if (comparator == Comparator.SMALLER) {
			if (artifactCount < targetCount) {
				return 1;
			} else {
				int delta = Math.abs(artifactCount - targetCount);
				return 1 / delta;
			}
		}

		return 0;
	}

}
