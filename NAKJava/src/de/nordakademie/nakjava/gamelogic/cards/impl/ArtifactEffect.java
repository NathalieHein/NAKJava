package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public abstract @interface ArtifactEffect {
	Class<? extends Artifact> artifact();

	int count();

	de.nordakademie.nakjava.gamelogic.cards.impl.Target target();

	public String metaText = "{count} {artifact.getSimpleName} für {target.description}";
}
