package de.nordakademie.nakjava.gamelogic.shared.artifacts.factories;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactTupel;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ressource;

public enum Factory implements Artifact {
	STEINBRUCH(1, new ArtifactTupel(Ressource.ZIEGEL, 1)), VERLIES(1,
			new ArtifactTupel(Ressource.MONSTER, 1)), ZAUBERLABOR(1,
			new ArtifactTupel(Ressource.KRISTALLE, 1));

	private int initialValue;
	private ArtifactTupel[] products;

	private Factory(int initialValue, ArtifactTupel... products) {
		// if (products.length != counts.length) {
		// throw new IllegalArgumentException(
		// "Number of products and counts has to match.");
		// }

		this.initialValue = initialValue;
		this.products = products;
	}

	@Override
	public Collection<ArtifactTupel> prePlayAction(ArtifactTupel ownTupel) {
		List<ArtifactTupel> result = new LinkedList<>();

		if (products != null) {
			for (ArtifactTupel product : products) {
				result.add(new ArtifactTupel(product.getArtifact(), product
						.getCount() * ownTupel.getCount()));
			}
		}

		return result;
	}

	@Override
	public Collection<ArtifactTupel> postPlayAction(ArtifactTupel ownTupel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactTupel getInitialValue() {
		return new ArtifactTupel(this, initialValue);
	}

}
