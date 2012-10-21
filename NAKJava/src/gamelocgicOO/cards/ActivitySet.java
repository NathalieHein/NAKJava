package gamelocgicOO.cards;

public class ActivitySet implements Activity {
	private Activity[] activities;

	// for now whole array, might be changed later to add-Method
	public ActivitySet(Activity[] activities) {
		this.activities = activities;
	}

	@Override
	public void perform() {
		for (Activity activity : activities) {
			activity.perform();
		}
	}

}
