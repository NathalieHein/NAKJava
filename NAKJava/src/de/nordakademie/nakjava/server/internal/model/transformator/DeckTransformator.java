package de.nordakademie.nakjava.server.internal.model.transformator;

import de.nordakademie.nakjava.server.internal.model.Transformator;
import de.nordakademie.nakjava.server.persistence.Deck;

/**
 * Transforms a Deck into its name
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class DeckTransformator implements Transformator<Deck, String> {

	@Override
	public String transform(Deck input) {
		return input.getName();
	}

}
