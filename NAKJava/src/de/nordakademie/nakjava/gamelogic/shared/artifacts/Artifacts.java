package de.nordakademie.nakjava.gamelogic.shared.artifacts;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.util.classpathschanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathschanner.ClasspathScanner;

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
		List<Class<?>> enums = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.gamelogic.shared.artifacts",
				"de.nordakademie.nakjava.artifactPackages",
				new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class<?> clazz) {
						return clazz.isEnum()
								&& Artifact.class.isAssignableFrom(clazz);
					}
				});

		for (Class<?> enumClass : enums) {
			for (Object enumValue : enumClass.getEnumConstants()) {

				instance.artifacts.add((Artifact) enumValue);
			}
		}

	}

	public List<Artifact> getArtifacts() {
		return artifacts;
	}

	public static Artifacts getInstance() {
		return instance;
	}
}
