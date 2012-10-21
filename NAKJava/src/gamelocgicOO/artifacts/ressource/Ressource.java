package gamelocgicOO.artifacts.ressource;

import gamelocgicOO.artifacts.ArtifactImpl;
import gamelocgicOO.cards.ArtifactEffectCaller;

public abstract class Ressource extends ArtifactImpl {

	public Ressource(int i) {
		value = i;
	}

	public void minus(Ressource minuend) {
		value = value - minuend.getValue();
	}

	public Ressource plus(Ressource summand) {
		return new Ressource(this.value - summand.value);
	}

	@Override
	public void alter(ArtifactEffectCaller effect) {
		// TODO Auto-generated method stub

	}
}
