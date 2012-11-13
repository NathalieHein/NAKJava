package de.nordakademie.nakjava.server.internal.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;

public class InGameSpecificModel implements StateSpecificModel {
	// really including stop-field or me that state opponent that state -> that
	// information
	@VisibleField(targets = {
			@TargetInState(states = { State.PLAYCARDSTATE, State.STOP }, target = Target.SELF),
			@TargetInState(states = { State.PLAYCARDSTATE, State.STOP }, target = Target.OPPONENT) })
	private List<? extends Artifact> artifacts;
	@VisibleField(targets = { @TargetInState(states = { State.PLAYCARDSTATE,
			State.ADJUSTCARDHANDSTATE, State.STOP }, target = Target.SELF) })
	private CardSet cards;

	// EnumMap not possible because of different enums
	private Map<Class<? extends Artifact>, Integer> cache = new HashMap<>();

	public InGameSpecificModel(List<? extends Artifact> initialArtifacts) {
		this.artifacts = initialArtifacts;
		this.cards = new CardSet();
	}

	public List<? extends Artifact> getArtifacts() {
		return artifacts;
	}

	public <T extends Artifact> T getTupelForClass(Class<T> searchedArtifact) {
		T lookupArtifact = cacheLookup(searchedArtifact);

		if (lookupArtifact == null) {
			for (int i = 0; i < artifacts.size(); i++) {
				if (artifacts.get(i).getClass().equals(searchedArtifact)) {
					cache.put(searchedArtifact, i);
					// Look two lines above...
					lookupArtifact = (T) artifacts.get(i);
					break;
				}
			}
			if (lookupArtifact == null) {
				throw new IllegalStateException("Fatal: Artifact: "
						+ searchedArtifact + " is not initialized.");
			}
		}

		return lookupArtifact;
	}

	public <T extends Artifact> List<T> getTupelsForArtifactType(
			Class<T> artifactType) {

		List<T> result = new LinkedList<>();

		for (Artifact artifact : artifacts) {
			if (artifactType.isAssignableFrom(artifact.getClass())) {
				// isAssignableFrom=true
				result.add((T) artifact);
			}
		}

		return result;
	}

	private <T extends Artifact> T cacheLookup(Class<T> artifact) {
		Integer artifactPointer = cache.get(artifact);

		if (artifactPointer == null) {
			return null;
		}

		Artifact foundArtifact = artifacts.get(artifactPointer);
		if (foundArtifact == null || !foundArtifact.getClass().equals(artifact)) {
			cache.remove(artifact);
			return null;
		}

		// Class is equal
		return (T) foundArtifact;
	}

	public CardSet getCards() {
		return cards;
	}

	public void setCards(Set<String> cardNames) {
		cards = new CardSet(cardNames);
	}
}