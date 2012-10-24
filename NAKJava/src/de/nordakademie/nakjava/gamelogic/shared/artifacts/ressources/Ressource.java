package de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public abstract class Ressource extends Artifact {

	@Override
	public int getInitialValue() {
		return 15;
	}

}
