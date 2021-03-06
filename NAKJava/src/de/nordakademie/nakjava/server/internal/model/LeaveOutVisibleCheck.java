package de.nordakademie.nakjava.server.internal.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to leave out visibility checks
 * 
 * @author Nathalie Hein (12154)
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface LeaveOutVisibleCheck {

}
