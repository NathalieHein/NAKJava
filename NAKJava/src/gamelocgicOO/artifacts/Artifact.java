package gamelocgicOO.artifacts;

import gamelocgicOO.cards.ArtifactEffectCaller;

public interface Artifact {
	public void alter(ArtifactEffectCaller effect);

	// I don't know whether this (and eventually the other convenience-methods)
	// make sense here
	// -> InfrastructureSet comparible???
	public boolean isGreaterThan(int value);

}
