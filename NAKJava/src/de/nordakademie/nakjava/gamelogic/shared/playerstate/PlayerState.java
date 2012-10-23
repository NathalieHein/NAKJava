package de.nordakademie.nakjava.gamelogic.shared.playerstate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactTupel;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Factory;

public class PlayerState {

	private List<ArtifactTupel> artifacts;

	// EnumMap not possible because of different enums
	private Map<Enum<? extends Artifact>, Integer> cache = new HashMap<>();

	public PlayerState(List<ArtifactTupel> initialArtifacts) {
		this.artifacts = initialArtifacts;
	}

	public List<ArtifactTupel> getArtifacts() {
		return artifacts;
	}

	public ArtifactTupel getTupelForArtifact(
			Enum<? extends Artifact> searchedArtifact) {
		ArtifactTupel lookupTupel = cacheLookup(searchedArtifact);

		if (lookupTupel == null) {
			for (int i = 0; i < artifacts.size(); i++) {
				if (artifacts.get(i).getArtifact() == searchedArtifact) {
					cache.put(searchedArtifact, i);
					lookupTupel = artifacts.get(i);
					break;
				}
			}
			if (lookupTupel == null) {
				throw new IllegalStateException("Fatal: Artifact: "
						+ searchedArtifact + " is not initialized.");
			}
		}

		getTupelForArtifact(Factory.STEINBRUCH);
		return lookupTupel;
	}

	@SuppressWarnings("unchecked")
	public List<ArtifactTupel> getTupelsForArtifactType(
			Class<? extends Artifact> artifactType) {
		if (!artifactType.isEnum()) {
			throw new IllegalArgumentException(
					"Fatal: Classes that extend Artifact need to be enums!");
		}

		List<ArtifactTupel> result = new LinkedList<>();

		for (Artifact artifact : artifactType.getEnumConstants()) {
			// we ARE iterating the enum constants...
			result.add(getTupelForArtifact((Enum<? extends Artifact>) artifact));
		}

		return result;
	}

	private ArtifactTupel cacheLookup(Enum<? extends Artifact> artifact) {
		Integer tupelPointer = cache.get(artifact);

		if (tupelPointer == null) {
			return null;
		}

		ArtifactTupel tupel = artifacts.get(tupelPointer);
		if (tupel == null || tupel.getArtifact() != artifact) {
			cache.remove(artifact);
			return null;
		}

		return tupel;
	}
}
