package de.nordakademie.nakjava.util.classpathscanner;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ClasspathScanner {

	@SuppressWarnings("unchecked")
	// Eclipse is not intelligent enough...
	public static <T> List<Class<T>> findClasses(String packagge,
			String additionalPackageProperty, ClassAcceptor<T> acceptor,
			final Class<T> baseClass) {

		@SuppressWarnings("rawtypes")
		// Generics are not possible here...
		List<Class<?>> tempClasses = findClasses(packagge,
				additionalPackageProperty, new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class clazz) {
						return baseClass.isAssignableFrom(clazz);
					}

				});

		List<Class<T>> resultList = new LinkedList<>();

		for (Class<?> tempClazz : tempClasses) {

			if (acceptor.acceptClass((Class<T>) tempClazz)) {
				resultList.add((Class<T>) tempClazz);
			}
		}

		return resultList;
	}

	// Generics end here..
	public static List<Class<?>> findClasses(String packagge,
			String additionalPackageProperty, ClassAcceptor acceptor) {
		List<Class<?>> classes = new LinkedList<>();

		String additionalPackageNames = "".equals(additionalPackageProperty) ? ""
				: System.getProperty(additionalPackageProperty, "");

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
