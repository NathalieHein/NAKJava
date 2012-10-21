package gamelocgicOO.cards;

public class Card {
	private Activity activity;
	private CardInformation info;

	public Card(Activity activity, CardInformation info) {
		this.setActivity(activity);
		this.setInfo(info);
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public CardInformation getInfo() {
		return info;
	}

	public void setInfo(CardInformation info) {
		this.info = info;
	}

}
