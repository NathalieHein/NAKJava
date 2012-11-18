package de.nordakademie.nakjava.client.bot.genericbot.transformers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;

/**
 * Annotates transformers. Transformers transform digital {@link WinCheck}s into
 * {@link WinCheckMeasurement}s which are analoge. This enables checking how far
 * you have reached your goal of winning.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface CheckTransformer {
	Class<? extends WinCheck> transformTarget();
}
