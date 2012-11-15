package de.nordakademie.nakjava.server.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Deck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9068715499591200395L;
	private String name;
	private HashSet<String> cards;

	public Deck(String name, Set<String> cards) {
		this.name = name;
		this.cards = new HashSet<>();
		for (String string : cards) {
			this.cards.add(string);
		}
	}

	public Deck() {
		this("", new HashSet<String>());
	}

	public String getName() {
		return name;
	}

	public Set<String> getCards() {
		return cards;
	}
}
