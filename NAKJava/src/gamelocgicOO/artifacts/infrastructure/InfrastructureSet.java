package gamelocgicOO.artifacts.infrastructure;

import gamelocgicOO.cards.ArtifactEffectCaller;

public class InfrastructureSet implements Infrastructure {
	private Infrastructure[] infrastructures;

	@Override
	public void alter(ArtifactEffectCaller effect) {
		for (Infrastructure infrastructure : infrastructures) {
			infrastructure.alter(effect);
		}
	}

	@Override
	public boolean isGreaterThan(int value) {
		// TODO Auto-generated method stub
		return false;
	}

}
