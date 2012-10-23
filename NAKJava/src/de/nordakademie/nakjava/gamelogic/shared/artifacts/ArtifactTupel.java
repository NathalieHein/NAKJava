package de.nordakademie.nakjava.gamelogic.shared.artifacts;

import java.util.Collection;

public class ArtifactTupel {

	private Artifact artifact;
	private int count;

	public ArtifactTupel(Artifact artifact, int count) {
		this.artifact = artifact;
		this.count = count;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Collection<ArtifactTupel> prePlayAction() {
		return artifact.prePlayAction(this);
	}

	public Collection<ArtifactTupel> postPlayAction() {
		return artifact.postPlayAction(this);
	}

	public void melt(ArtifactTupel other) {

		if (this.artifact != other.artifact) {
			throw new IllegalArgumentException("Could not melt "
					+ this.artifact + " with another artifact: "
					+ other.artifact);
		}

		this.count += other.count;
	}

}
