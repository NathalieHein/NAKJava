package de.nordakademie.nakjava.server.persistence;

import java.io.Serializable;
import java.util.Set;

public class Deck implements Serializable {
	private String name;
	private Set<String> cards;

	public Deck(String name, Set<String> cards) {
		this.name = name;
		this.cards = cards;
	}

	public String getName() {
		return name;
	}

	public Set<String> getCards() {
		return cards;
	}
}
