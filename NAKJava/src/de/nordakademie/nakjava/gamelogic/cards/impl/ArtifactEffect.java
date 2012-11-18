package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

/**
 * Artifact effects change the an given artifact of a player.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public abstract @interface ArtifactEffect {
	Class<? extends Artifact> artifact();

	int count();

	de.nordakademie.nakjava.gamelogic.cards.impl.Target target();

	/**
	 * Description which will be generated on a cardInformation
	 */
	public String metaText = "{count} {artifact.getSimpleName} für {target.getAkkusativDescription}";
}
