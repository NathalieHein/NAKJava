package test;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class StateMachine {
	private Map<StackState, StateListener> listeners = new HashMap<>();
	private Deque<StackState> stack = new ArrayDeque<>();

	public void nextElement() {
		StackState state = stack.peek();
		if (state == null) {
			// Do Something here...
		}

		if (listeners.get(state) != null) {
			listeners.get(state).perform(stack);
		} else {
			stack.pop();
			state.perfomAction();
		}
	}

	public static void main(String[] args) {
		StateMachine machine = new StateMachine();
	}
}
