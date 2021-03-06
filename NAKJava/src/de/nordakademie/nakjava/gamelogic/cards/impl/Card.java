package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;

/**
 * Annotation for tagging cards. Tagged cards must inherit from
 * {@link AbstractCard} and will be automatically instantiated. The name of the
 * card is assumed to be unique. Normally a DSL would be used for such problems.
 * Due to the task this is not possible. XML is not typesafe though, so there is
 * no other way :-)
 * 
 * @author Kai
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Card {
	/**
	 * Unique name of the card
	 * 
	 * @return
	 */
	String name();

	String additionalDescription() default "";

	CardType type();

	Cost[] costs() default {};

	ArtifactEffect[] artifactEffects() default {};

	DamageEffect[] damageEffects() default {};

	boolean canDrop() default true;
}
