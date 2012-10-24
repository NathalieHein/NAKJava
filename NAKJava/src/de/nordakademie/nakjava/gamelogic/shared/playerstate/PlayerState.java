package de.nordakademie.nakjava.gamelogic.shared.playerstate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public class PlayerState {

	private List<Artifact> artifacts;

	// EnumMap not possible because of different enums
	private Map<Class<? extends Artifact>, Integer> cache = new HashMap<>();

	public PlayerState(List<Artifact> initialArtifacts) {
		this.artifacts = initialArtifacts;
	}

	public List<Artifact> getArtifacts() {
		return artifacts;
	}

	public Artifact getTupelForClass(Class<? extends Artifact> searchedArtifact) {
		Artifact lookupArtifact = cacheLookup(searchedArtifact);

		if (lookupArtifact == null) {
			for (int i = 0; i < artifacts.size(); i++) {
				if (artifacts.get(i).getClass().equals(searchedArtifact)) {
					cache.put(searchedArtifact, i);
					lookupArtifact = artifacts.get(i);
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

	@SuppressWarnings("unchecked")
	public List<Artifact> getTupelsForArtifactType(
			Class<? extends Artifact> artifactType) {

		List<Artifact> result = new LinkedList<>();

		for (Artifact artifact : artifacts) {
			if (artifactType.isAssignableFrom(artifact.getClass())) {
				result.add(artifact);
			}
		}

		return result;
	}

	private Artifact cacheLookup(Class<? extends Artifact> artifact) {
		Integer artifactPointer = cache.get(artifact);

		if (artifactPointer == null) {
			return null;
		}

		Artifact foundArtifact = artifacts.get(artifactPointer);
		if (foundArtifact == null || !foundArtifact.getClass().equals(artifact)) {
			cache.remove(artifact);
			return null;
		}

		return foundArtifact;
	}
}
