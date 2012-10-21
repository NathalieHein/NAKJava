package gamelocgicOO.cards;

public class ArtifactEffect extends Effect implements ArtifactEffectCaller {

	private int difference;

	@Override
	public void perform() {
		// TODO Auto-generated method stub

	}

	@Override
	public int call(int currentArtifactValue) {
		int temp;
		if (difference > 0) {
			temp = difference;
			difference = 0;
			return currentArtifactValue + temp;
		} else if (difference < 0) {
			temp = difference;
			difference = difference - currentArtifactValue;
			return currentArtifactValue - temp;
		}
		return currentArtifactValue;
	}

}
