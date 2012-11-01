package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;

public class PlayerModel implements Serializable {
	private Map<Target, State> targetToState;
	private List<? extends Artifact> artifacts;
	private List<CardInformation> cardHand;

	public List<? extends Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<? extends Artifact> artifacts) {
		this.artifacts = artifacts;
	}

	public List<CardInformation> getCardHand() {
		return cardHand;
	}

	public void setCardHand(List<CardInformation> cardHand) {
		this.cardHand = cardHand;
	}

	public Artifact getArtifactForClass(Class<? extends Artifact> artifact) {
		for (Artifact currentArtifact : artifacts) {
			if (currentArtifact.getClass().equals(artifact)) {
				return currentArtifact;
			}
		}
		throw new IllegalArgumentException("No Artifact for type: "
				+ artifact.getSimpleName());
	}

	public Map<Target, State> getTargetToState() {
		return targetToState;
	}

	public void setTargetToState(Map<Target, State> targetToState) {
		this.targetToState = targetToState;
	}

}
