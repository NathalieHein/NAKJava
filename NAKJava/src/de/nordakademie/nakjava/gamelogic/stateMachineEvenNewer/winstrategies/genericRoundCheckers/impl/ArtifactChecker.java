package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.impl;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.Comparator;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.util.StringUtilities;

public class ArtifactChecker implements WinCheck, Serializable {

	private Class<? extends Artifact>[] artifacts;
	private Target target;
	private int count;
	private Comparator comparator;
	private boolean and;

	public static final boolean AND = true;
	public static final boolean OR = false;

	public ArtifactChecker(Target target,
			Class<? extends Artifact>[] artifacts, Comparator comparator,
			int count, boolean and) {
		this.target = target;
		this.artifacts = artifacts;
		this.comparator = comparator;
		this.count = count;
		this.and = and;
	}

	@Override
	public RoundResult check(Map<Target, PlayerState> states) {
		PlayerState state = states.get(target);

		boolean won = and;

		for (Class<? extends Artifact> artifact : artifacts) {

			List<Artifact> toCheck = new LinkedList<>();

			if (Modifier.isAbstract(artifact.getModifiers())) {
				toCheck.addAll(((InGameSpecificModel) state
						.getStateSpecificModel())
						.getTupelsForArtifactType(artifact));
			} else {
				toCheck.add(((InGameSpecificModel) state
						.getStateSpecificModel()).getTupelForClass(artifact));
			}

			for (Artifact checkArtifact : toCheck) {

				if (comparator == Comparator.GREATER) {
					if (and) {
						won &= checkArtifact.getCount() > count;
					} else {
						won |= checkArtifact.getCount() > count;
					}

				} else if (comparator == Comparator.SMALLER) {
					if (and) {
						won &= checkArtifact.getCount() < count;
					} else {
						won |= checkArtifact.getCount() < count;
					}
				} else {
					if (and) {
						won &= checkArtifact.getCount() == count;
					} else {
						won |= checkArtifact.getCount() == count;
					}
				}

				if (and) {
					if (!won) {
						break;
					}
				} else {
					if (won) {
						break;
					}
				}
			}

		}

		if (won) {
			return RoundResult.WIN;
		} else {
			return RoundResult.NEUTRAL;
		}
	}

	public Class<? extends Artifact>[] getArtifacts() {
		return artifacts;
	}

	public Target getTarget() {
		return target;
	}

	public int getCount() {
		return count;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public boolean getOperator() {
		return and;
	}

	@Override
	public String getDescription() {

		List<String> artifacts = new LinkedList<>();
		for (Class<? extends Artifact> artifactClazz : this.artifacts) {
			artifacts.add(artifactClazz.getSimpleName());
		}

		return target.getNominativDescription() + " "
				+ comparator.getDescription() + " " + count
				+ (and ? "je " : " ")
				+ StringUtilities.concatWithDelimiter(artifacts, ", ");
	}

}
