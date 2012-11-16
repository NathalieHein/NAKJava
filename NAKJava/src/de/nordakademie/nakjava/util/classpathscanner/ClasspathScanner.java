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

public class ClasspathScanner {

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
							e.printStackTrace();
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
							e.printStackTrace();
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

	// Generics end here..
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
