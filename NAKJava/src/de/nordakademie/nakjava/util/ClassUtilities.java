package de.nordakademie.nakjava.util;

import java.lang.reflect.Modifier;

public final class ClassUtilities {
	private ClassUtilities() {
	}

	public static <T> Class<? super T> getNonAbstractSuperClass(Class<T> clazz) {
		if (Modifier.isAbstract(clazz.getSuperclass().getModifiers())) {
			return clazz;
		} else {
			return getNonAbstractSuperClass(clazz.getSuperclass());
		}
	}
}
