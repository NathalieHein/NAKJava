package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.impl;

import java.io.Serializable;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.Comparator;

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

			if (comparator == Comparator.GREATER) {
				if (and) {
					won &= state.getTupelForClass(artifact).getCount() > count;
				} else {
					won |= state.getTupelForClass(artifact).getCount() > count;
				}

			} else if (comparator == Comparator.SMALLER) {
				if (and) {
					won &= state.getTupelForClass(artifact).getCount() < count;
				} else {
					won |= state.getTupelForClass(artifact).getCount() < count;
				}
			} else {
				if (and) {
					won &= state.getTupelForClass(artifact).getCount() == count;
				} else {
					won |= state.getTupelForClass(artifact).getCount() == count;
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

}
