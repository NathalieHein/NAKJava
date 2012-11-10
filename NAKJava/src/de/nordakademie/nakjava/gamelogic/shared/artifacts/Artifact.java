package de.nordakademie.nakjava.gamelogic.shared.artifacts;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Artifact {

	protected int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Collection<Artifact> prePlayAction() {
		return new ArrayList<>();
	}

	public Collection<Artifact> postPlayAction() {
		return new ArrayList<>();
	}

	public int getInitialValue() {
		return 1;
	}

	public int getMinimalValue() {
		return 0;
	}

	public void merge(Artifact other) {
		if (!this.getClass().equals(other.getClass())) {
			throw new IllegalArgumentException(this.getClass().getName()
					+ " can not be merged with " + other.getClass().getName());
		}

		this.count += other.count;
		if (this.count < getMinimalValue()) {
			this.count = getMinimalValue();
		}
	}

}
