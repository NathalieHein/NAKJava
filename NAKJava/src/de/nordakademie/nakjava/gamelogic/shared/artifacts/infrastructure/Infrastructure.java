package de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public abstract class Infrastructure extends Artifact implements
		Comparable<Infrastructure> {

	public abstract int getPosition();

	@Override
	public int compareTo(Infrastructure o) {
		return new Integer(this.getPosition()).compareTo(o.getPosition());
	}

}
