package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

public class PlayCardSpecificInformation implements StateSpecificInformation {

	private List<? extends Artifact> ownArtifacts;
	private List<CardInformation> cardHand;
	private CardInformation opponentsPlayedCard;
	private List<? extends Artifact> opponentsArtifacts;

	public List<? extends Artifact> getOwnArtifacts() {
		return ownArtifacts;
	}

	public void setOwnArtifacts(List<? extends Artifact> artifacts) {
		this.ownArtifacts = artifacts;
	}

	public List<CardInformation> getCardHand() {
		return cardHand;
	}

	public void setCardHand(List<CardInformation> cardHand) {
		this.cardHand = cardHand;
	}

	public Artifact getArtifactForClass(Class<? extends Artifact> artifact) {
		for (Artifact currentArtifact : ownArtifacts) {
			if (currentArtifact.getClass().equals(artifact)) {
				return currentArtifact;
			}
		}
		throw new IllegalArgumentException("No Artifact for type: "
				+ artifact.getSimpleName());
	}

	public CardInformation getOpponentsPlayedCard() {
		return opponentsPlayedCard;
	}

	public void setOpponentsPlayedCard(CardInformation opponentsPlayedCard) {
		this.opponentsPlayedCard = opponentsPlayedCard;
	}

	public List<? extends Artifact> getOpponentsArtifacts() {
		return opponentsArtifacts;
	}

	public void setOpponentsArtifacts(List<? extends Artifact> opponentsArtifacts) {
		this.opponentsArtifacts = opponentsArtifacts;
	}
}
