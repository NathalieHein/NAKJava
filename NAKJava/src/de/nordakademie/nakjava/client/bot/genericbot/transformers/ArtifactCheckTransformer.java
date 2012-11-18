package de.nordakademie.nakjava.client.bot.genericbot.transformers;

import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.Comparator;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.impl.ArtifactChecker;

/**
 * Transforms artifact checks into measurements. This enables a distinct
 * perspective on "how near you are". It considers the type of artifact, target,
 * comparator and count.
 * 
 * 
 */
@CheckTransformer(transformTarget = ArtifactChecker.class)
public class ArtifactCheckTransformer implements WinCheckTransformer {

	@Override
	public WinCheckMeasurement transform(WinCheck check) {

		final ArtifactChecker checker = (ArtifactChecker) check;

		return new WinCheckMeasurement() {

			@Override
			public double measure(Map<Target, List<? extends Artifact>> model) {

				List<? extends Artifact> pModel = model
						.get(checker.getTarget());

				List<? extends Artifact> oModel;

				if (checker.getTarget() == Target.SELF) {
					oModel = model.get(Target.OPPONENT);
				} else {
					oModel = model.get(Target.SELF);
				}

				double result = check(pModel);

				double opponentResult = check(oModel);

				if (opponentResult == 1) {
					return 0;
				} else {
					return result;
				}
			}

			private double check(List<? extends Artifact> pModel) {
				double result = 0.0;

				if (checker.getOperator() == ArtifactChecker.AND) {

					int count = 0;

					for (Class<? extends Artifact> artifact : checker
							.getArtifacts()) {
						Artifact currentArtifact = getArtifactFrom(pModel,
								artifact);

						result += checkArtifact(currentArtifact.getCount(),
								checker.getComparator(), checker.getCount());
						count++;
					}

					result /= count;
				} else {

					for (Class<? extends Artifact> artifact : checker
							.getArtifacts()) {
						Artifact currentArtifact = getArtifactFrom(pModel,
								artifact);

						double tempResult = checkArtifact(currentArtifact
								.getCount(), checker.getComparator(), checker
								.getCount());
						if (tempResult > result) {
							result = tempResult;
						}
					}
				}

				return result;
			}
		};
	}

	/**
	 * Artifact checking algorithm.
	 * 
	 * @param artifactCount
	 *            current count
	 * @param comparator
	 *            <,>,=
	 * @param targetCount
	 *            desired count
	 * @return measurement 0(beginning)..1(won)
	 */
	private double checkArtifact(int artifactCount, Comparator comparator,
			int targetCount) {
		if (comparator == Comparator.EQUAL) {
			if (artifactCount == targetCount) {
				return 1;
			} else {
				double delta = Math.abs(artifactCount - targetCount);
				return delta / artifactCount;
			}
		}
		if (comparator == Comparator.GREATER) {
			if (artifactCount > targetCount) {
				return 1;
			} else {
				double delta = Math.abs(artifactCount - targetCount);
				return delta / targetCount;
			}
		}
		if (comparator == Comparator.SMALLER) {
			if (artifactCount < targetCount) {
				return 1;
			} else {
				double delta = Math.abs(artifactCount - targetCount);
				return 1 / delta;
			}
		}

		return 0;
	}

	/**
	 * Returns a certain artifact, which is denoted as a Class from a List of
	 * artifacts
	 * 
	 * @param artifacts
	 *            list of artifacts
	 * @param artifactClass
	 *            class of artifact
	 * @return Artifact object from the searched class
	 */
	private Artifact getArtifactFrom(List<? extends Artifact> artifacts,
			Class<? extends Artifact> artifactClass) {

		for (Artifact artifact : artifacts) {
			if (artifactClass.equals(artifact.getClass())) {
				return artifact;
			}
		}

		return null;
	}

}
