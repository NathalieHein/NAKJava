package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.State;

public class PlayerModel implements Serializable {
	private State selfState;
	private State opponentState;
	private List<? extends Artifact> artifacts;
	private List<CardInformation> cardHand;
	public State getSelfState() {
		return selfState;
	}
	public void setSelfState(State selfState) {
		this.selfState = selfState;
	}
	public State getOpponentState() {
		return opponentState;
	}
	public void setOpponentState(State opponentState) {
		this.opponentState = opponentState;
	}
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

}
