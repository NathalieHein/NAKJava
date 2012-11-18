package de.nordakademie.nakjava.gamelogic.util;

import junit.framework.Assert;

import org.junit.Test;

import de.nordakademie.nakjava.util.DeepCopyUtility;

public class TestDeepCopyUtility {

	@Test
	public void test() {
		TestBean bean = new TestBean("test", 2);
		TestBean copied = DeepCopyUtility.deepCopy(bean);
		Assert.assertFalse(bean == copied);
		Assert.assertTrue(bean.equals(copied));
	}
}
