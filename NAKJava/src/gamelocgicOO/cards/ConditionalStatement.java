package gamelocgicOO.cards;

public class ConditionalStatement implements Activity {
	private Condition condition;
	private Activity activity;

	public ConditionalStatement(Condition condition, Activity activity) {
		this.condition = condition;
		this.activity = activity;
	}

	@Override
	public void perform() {
		if (condition.isTrue()) {
			activity.perform();
		}
	}
}