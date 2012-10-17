package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.shared.infrastructure.Infrastructure;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface InfrastructureEffect {
	Infrastructure infrastructure();

	int amount();
}
