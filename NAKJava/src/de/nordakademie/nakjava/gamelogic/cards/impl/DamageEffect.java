package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Damage effect, which damages the infrastructure in their order of existence.
 * 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface DamageEffect {
	int count();

	de.nordakademie.nakjava.gamelogic.cards.impl.Target target();

	public String metaText = "{count} Schaden für {target.getAkkusativDescription}";
}
