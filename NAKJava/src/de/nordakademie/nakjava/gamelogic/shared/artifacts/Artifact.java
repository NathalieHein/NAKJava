package de.nordakademie.nakjava.gamelogic.shared.artifacts;

import java.util.Collection;

public interface Artifact {

	public Collection<ArtifactTupel> prePlayAction(ArtifactTupel ownTupel);

	public Collection<ArtifactTupel> postPlayAction(ArtifactTupel ownTupel);

	public ArtifactTupel getInitialValue();
}
