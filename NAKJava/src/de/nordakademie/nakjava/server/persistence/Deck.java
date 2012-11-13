package de.nordakademie.nakjava.server.persistence;

import java.io.Serializable;
import java.util.List;

public class Deck implements Serializable {
	private String name;
	private List<String> cards;

	public Deck(String name, List<String> cards) {
		this.name = name;
		this.cards = cards;
	}

	public String getName() {
		return name;
	}

	public List<String> getCards() {
		return cards;
	}
}
