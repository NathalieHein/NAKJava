package de.nordakademie.nakjava.util.classpathscanner;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.util.StringUtilities;

/**
 * Scans the whole classpath for certain classes which may have annotation or
 * extends a certain class. This is used for building up dependencies on runtime
 * and decoupling dependencies.
 */
public class ClasspathScanner {

	/**
	 * By invoking this method all methods annotated with {@link ClassLookup}
	 * annotation will be performed. All uses of classpathScanners do not need
	 * to be known. Scanning the whole classpath for the application is possible
	 * because 1.) The classes are mostly all used when deployed in the right
	 * way 2.) No javaassist is usable
	 */
	public static void lookupAnnotatedScanners() {
		findClasses("de.nordakademie.nakjava", "", new ClassAcceptor() {

			@Override
			public boolean acceptClass(Class clazz) {

				for (Method method : clazz.getMethods()) {
					if (method.isAnnotationPresent(ClassLookup.class)) {
						try {
							method.invoke(null, null);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							throw new IllegalArgumentException(e);
						}
						return true;
					}
				}
				for (Method method : clazz.getDeclaredMethods()) {
					if (method.isAnnotationPresent(ClassLookup.class)) {
						try {
							method.setAccessible(true);
							method.invoke(null, null);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							throw new IllegalArgumentException(e);
						}
						return true;
					}
				}

				return false;
			}
		});
	}

	public static <T> List<Class<T>> findClasses(String packagge,
			String additionalPackageProperty, final Class<T> baseClass) {
		return findClasses(packagge, additionalPackageProperty, null, baseClass);
	}

	@SuppressWarnings("unchecked")
	// Eclipse is not intelligent enough...
	public static <T> List<Class<T>> findClasses(String packagge,
			String additionalPackageProperty, ClassAcceptor<T> acceptor,
			final Class<T> baseClass) {

		@SuppressWarnings("rawtypes")
		List<Class<?>> tempClasses = findClasses(packagge,
				additionalPackageProperty, new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class clazz) {
						return baseClass.isAssignableFrom(clazz);
					}

				});

		List<Class<T>> resultList = new LinkedList<>();

		for (Class<?> tempClazz : tempClasses) {

			if (acceptor == null || acceptor.acceptClass((Class<T>) tempClazz)) {
				resultList.add((Class<T>) tempClazz);
			}
		}

		return resultList;

	}

	/**
	 * Finds all classes which are feasible for the passed {@link ClassAcceptor}
	 * . This enables DI at runtime.
	 * 
	 * @param packagge
	 *            package in which to lookup the classes - otherwise it would
	 *            scan the whole jre
	 * @param additionalPackageProperty
	 *            other packages can always be passed via system property so the
	 *            programm can be enhanced without changing code
	 * @param acceptor
	 * @returnthe List of found classes
	 */
	// Generics end here...
	public static List<Class<?>> findClasses(String packagge,
			String additionalPackageProperty, ClassAcceptor acceptor) {
		List<Class<?>> classes = new LinkedList<>();

		String additionalPackageNames = StringUtilities
				.isNotNullOrEmpty(additionalPackageProperty) ? System
				.getProperty(additionalPackageProperty, "") : "";

		List<String> packages = Arrays.asList(((additionalPackageNames
				.equals("") ? "" : additionalPackageNames + ",") + packagge)
				.split(","));

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		for (String pack : packages) {
			URL resource = classLoader.getResource(pack.replace(".", "/"));
			File directory = null;

			try {
				directory = new File(resource.toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException(
						"Could not load file from packagename.");
			}

			String[] classNames = directory.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".class");
				}
			});

			String[] folderNames = directory.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return new File(dir.getPath() + "\\" + name).isDirectory();
				}
			});

			for (String className : classNames) {
				Class<?> clazz;

				try {
					clazz = Class.forName(pack + "."
							+ className.substring(0, className.length() - 6));
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Class could not be loaded.");
				}

				if (acceptor.acceptClass(clazz)) {
					classes.add(clazz);
				}
			}

			for (String folder : folderNames) {
				classes.addAll(findClasses(pack + "." + folder, "", acceptor));
			}
		}

		return classes;
	}
}
