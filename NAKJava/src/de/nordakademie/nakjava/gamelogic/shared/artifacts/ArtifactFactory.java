package de.nordakademie.nakjava.gamelogic.shared.artifacts;

public class ArtifactFactory {

	public static Artifact createArtifact(Class<? extends Artifact> clazz) {
		try {
			Artifact artifact = clazz.newInstance();
			artifact.setCount(artifact.getInitialValue());

			return artifact;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static <T extends Artifact> T cloneArtifact(T artifact) {
		Class<?> clazz = artifact.getClass();
		T clone = null;
		try {
			clone = (T) clazz.newInstance();
			clone.setCount(artifact.getCount());

			return clone;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clone;

	}

	public static Artifact createArtifact(Class<? extends Artifact> clazz,
			int count) {
		try {
			Artifact artifact = clazz.newInstance();
			artifact.setCount(count);

			return artifact;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
