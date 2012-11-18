package de.nordakademie.nakjava.gamelogic.shared.artifacts;

/**
 * Factory for generating artifacts due to the fact that they are accessed
 * through their classes. The factory initialzes the artifacts to the standard
 * value
 * 
 * 
 */
public class ArtifactFactory {

	public static Artifact createArtifact(Class<? extends Artifact> clazz) {
		try {
			Artifact artifact = clazz.newInstance();
			artifact.setCount(artifact.getInitialValue());

			return artifact;
		} catch (IllegalAccessException | InstantiationException e) {
			throw new IllegalArgumentException();
		}
	}

	public static <T extends Artifact> T cloneArtifact(T artifact) {
		Class<?> clazz = artifact.getClass();
		T clone = null;
		try {
			clone = (T) clazz.newInstance();
			clone.setCount(artifact.getCount());

			return clone;
		} catch (IllegalAccessException | InstantiationException e) {

			throw new IllegalArgumentException(e);
		}

	}

	public static Artifact createArtifact(Class<? extends Artifact> clazz,
			int count) {
		try {
			Artifact artifact = clazz.newInstance();
			artifact.setCount(count);

			return artifact;
		} catch (IllegalAccessException | InstantiationException e) {
			throw new IllegalArgumentException(e);
		}

	}
}
