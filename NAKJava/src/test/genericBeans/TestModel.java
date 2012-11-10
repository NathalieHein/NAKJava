package test.genericBeans;

import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField;

public class TestModel {

	@VisibleField(states = { State.LOGIN },
			targets = { Target.SELF })
	private String test;

	@VisibleField(states = { State.LOGIN },
			targets = { Target.SELF })
	private int[] test1;

	@VisibleField(states = { State.LOGIN },
			targets = { Target.SELF })
	private List<Integer> test2;
}
