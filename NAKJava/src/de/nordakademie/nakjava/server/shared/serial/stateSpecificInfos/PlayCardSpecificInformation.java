package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

public class PlayCardSpecificInformation {

	private List<? extends Artifact> artifacts;
	private List<CardInformation> cardHand;
	private String opponentsPlayedCard;

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

	public String getOpponentsPlayedCard() {
		return opponentsPlayedCard;
	}

	public void setOpponentsPlayedCard(String opponentsPlayedCard) {
		this.opponentsPlayedCard = opponentsPlayedCard;
	}
}
