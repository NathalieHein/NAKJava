package de.nordakademie.nakjava.gamelogic.util;

import java.io.Serializable;

public class TestBean implements Serializable {

	private String a = "a";
	private int b = 2;

	public TestBean(String a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof TestBean) {
			TestBean oBean = (TestBean) other;
			return a.equals(oBean.a) && b == oBean.b;
		}

		return false;
	}

}
