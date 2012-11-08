package de.nordakademie.nakjava.gamelogic.shared.artifacts;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class Artifacts {

	private static Artifacts instance;
	private List<Artifact> artifacts;

	private Artifacts() {
		artifacts = new LinkedList<>();
		instance = this;
	}

	public static void main(String[] args) {
		generateArtifacts();
	}

	public static void generateArtifacts() {
		new Artifacts();
		List<Class<Artifact>> artifacts = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.gamelogic.shared.artifacts",
				"de.nordakademie.nakjava.artifactPackages",
				new ClassAcceptor<Artifact>() {

					@Override
					public boolean acceptClass(Class<Artifact> clazz) {
						return !Modifier.isAbstract(clazz.getModifiers());
					}
				}, Artifact.class);

		for (Class<?> artifact : artifacts) {
			// isAssignableFrom does this magic
			instance.artifacts.add(ArtifactFactory
					.createArtifact((Class<? extends Artifact>) artifact));
		}

	}

	public List<Artifact> getInitialArtifacts() {
		List<Artifact> newArtifacts = new LinkedList<>();
		for (Artifact artifact : artifacts) {
			newArtifacts.add(ArtifactFactory.cloneArtifact(artifact));
		}
		return newArtifacts;
	}

	public static Artifacts getInstance() {
		return instance;
	}
}
