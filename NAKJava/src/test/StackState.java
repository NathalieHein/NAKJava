package test;

public class StackState {

	private StateAction action;

	public static final StackState STATE_A = new StackState(new StateAction() {

		@Override
		public void DoSomething() {
			// TODO Auto-generated method stub

		}
	});

	private StackState(StateAction action) {
		this.action = action;
	}
}
