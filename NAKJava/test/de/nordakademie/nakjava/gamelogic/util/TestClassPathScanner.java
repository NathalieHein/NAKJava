package de.nordakademie.nakjava.gamelogic.util;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class TestClassPathScanner {

	@Test
	public void test() {
		List<Class<TestInterface>> clazzes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.gamelogic.util", "",
				TestInterface.class);
		// 2 Classes + Interface is expected
		Assert.assertEquals(3, clazzes.size());
	}
}
