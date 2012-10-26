package de.nordakademie.nakjava.gamelogic.shared.artifacts.factories;

import java.util.Collection;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public abstract class Factory extends Artifact {

	@Override
	public Collection<Artifact> prePlayAction() {
		return produce();
	}

	@Override
	public int getMinimalValue() {
		return 1;
	}

	protected abstract Collection<Artifact> produce();

}
