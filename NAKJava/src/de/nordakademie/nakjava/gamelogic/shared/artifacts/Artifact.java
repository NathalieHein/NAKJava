package de.nordakademie.nakjava.gamelogic.shared.artifacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An artifact is passive value, it can have three types: factories,
 * infrastructure and ressources. Other types can be added by simply subclassing
 * artifact and adding behaviour.
 * 
 */
public abstract class Artifact implements Serializable {

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

	/**
	 * Two artifact objects of the same type can be merged (added) together. A
	 * minimal value of the merge target is also considered as well as re using
	 * the target object
	 * 
	 * @param other
	 */
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
