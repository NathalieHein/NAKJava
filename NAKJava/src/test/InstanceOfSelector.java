package test;

public class InstanceOfSelector<E> implements Selector<E> {

	Class testClass;

	public InstanceOfSelector(Class testClass) {
		this.testClass = testClass;
	}

	@Override
	public boolean select(E test) {
		testClass.equals(test.getClass());
		return testClass.isAssignableFrom(test.getClass());
	}
}
