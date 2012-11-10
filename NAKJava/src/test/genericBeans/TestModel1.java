package test.genericBeans;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField;

public class TestModel1 {

	@VisibleField(states = { State.LOGIN },
			targets = { Target.SELF })
	private int test;
}
