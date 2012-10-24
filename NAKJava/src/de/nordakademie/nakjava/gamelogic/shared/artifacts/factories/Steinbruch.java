package de.nordakademie.nakjava.gamelogic.shared.artifacts.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;

public class Steinbruch extends Factory {

	@Override
	protected Collection<Artifact> produce() {
		List<Artifact> result = new ArrayList<>();
		result.add(ArtifactFactory.createArtifact(Ziegel.class, count));
		return result;
	}

}
