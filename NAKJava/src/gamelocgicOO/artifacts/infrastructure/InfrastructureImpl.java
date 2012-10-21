package gamelocgicOO.artifacts.infrastructure;

import gamelocgicOO.artifacts.ArtifactImpl;
import gamelocgicOO.cards.ArtifactEffectCaller;

public class InfrastructureImpl extends ArtifactImpl implements Infrastructure {
	private int value;

	@Override
	public void alter(ArtifactEffectCaller effect) {
		value = effect.call(value);
	}
}
