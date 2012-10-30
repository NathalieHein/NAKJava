package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.ArtifactChecker;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WinStrategy {
	ArtifactChecker[] artifactCheckers() default {};
}
