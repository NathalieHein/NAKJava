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
