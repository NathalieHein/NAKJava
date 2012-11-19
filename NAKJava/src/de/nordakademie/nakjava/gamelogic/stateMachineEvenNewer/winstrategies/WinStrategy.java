package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.ArtifactChecker;

/**
 * Taggig interface for winstrategies. A new Strategy might be added by adding
 * an new class tagged with this annotation. This class must Subclass
 * {@link AbstractWinStrategy}
 * 
 * @author Kai
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WinStrategy {
	ArtifactChecker[] artifactCheckers() default {};
}
