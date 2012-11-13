package test.genericBeans;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.server.internal.model.VisibleField;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;

public class TestGeneric {
	@VisibleField(targets = { @TargetInState(target = Target.SELF,
			states = {}) },
			transformer = TestTransformator.class)
	private String value;
}
