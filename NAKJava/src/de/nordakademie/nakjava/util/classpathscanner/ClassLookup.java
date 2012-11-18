package de.nordakademie.nakjava.util.classpathscanner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * Annotation to tag a class scanning method. 
 * By invoking one method those methods are all invoked.
 *
 */
public @interface ClassLookup {
}
