package de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources;

import java.util.Collection;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactTupel;

public enum Ressource implements Artifact {
	ZIEGEL(15), MONSTER(15), KRISTALLE(15);

	private int initialValue;

	private Ressource(int initialValue) {
		this.initialValue = initialValue;
	}

	@Override
	public Collection<ArtifactTupel> prePlayAction(ArtifactTupel ownTupel) {
		return null;
	}

	@Override
	public Collection<ArtifactTupel> postPlayAction(ArtifactTupel ownTupel) {
		return null;
	}

	@Override
	public ArtifactTupel getInitialValue() {
		return new ArtifactTupel(this, initialValue);
	}

	public static void main(String[] args) {
		ArtifactTupel tupel = Ressource.ZIEGEL.getInitialValue();
	}
}
