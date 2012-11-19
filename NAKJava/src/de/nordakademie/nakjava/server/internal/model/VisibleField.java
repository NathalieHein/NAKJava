package de.nordakademie.nakjava.server.internal.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;

/**
 * Tags a value which is automatically collected and sent to the client. A
 * {@link VisibleField} object in {@link VisibleModelFields} is automatically
 * via apt generated so that there is a typesafe access to the value.
 * 
 */
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface VisibleField {
	/**
	 * The value is only submitted when the target is in a given State
	 * 
	 * @return
	 */
	public TargetInState[] targets();

	/**
	 * The value of a field can be transformed before serialization
	 * 
	 * @return
	 */
	public Class<? extends Transformator<?, ?>> transformer() default IdentityTransformator.class;

	public @interface TargetInState {
		public de.nordakademie.nakjava.gamelogic.cards.impl.Target target();

		public State[] states();
	}
}
