package de.nordakademie.nakjava.gamelogic.shared.playerstate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public class PlayerState {

	private List<? extends Artifact> artifacts;

	// EnumMap not possible because of different enums
	private Map<Class<? extends Artifact>, Integer> cache = new HashMap<>();

	public PlayerState(List<? extends Artifact> initialArtifacts) {
		this.artifacts = initialArtifacts;
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
}
