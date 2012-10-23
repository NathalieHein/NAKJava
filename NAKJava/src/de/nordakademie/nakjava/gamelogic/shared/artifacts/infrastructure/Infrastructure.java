package de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure;

import java.util.Collection;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactTupel;

/**
 * Represents Infrastructure {@link Artifact}s, these artifacts objects which
 * can take damage. The order of this enum is equivalent to the damage order.
 * 
 * @author Kai
 * 
 */
public enum Infrastructure implements Artifact {
	MAUER(10), TURM(25);

	private int initialValue;

	private Infrastructure(int initialValue) {
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

}
