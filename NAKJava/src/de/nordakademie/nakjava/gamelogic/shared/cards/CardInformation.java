package de.nordakademie.nakjava.gamelogic.shared.cards;

import java.io.Serializable;

import de.nordakademie.nakjava.gamelogic.cards.impl.Card;

/**
 * Contains human-readable metadata for cards. This class will be instantiated
 * from the {@link Card} annotation.
 * 
 */
public class CardInformation implements Serializable,
		Comparable<CardInformation> {
	private final String title;
	private final String information;
	private final String cost;
	private final CardType type;

	/**
	 * @param title
	 * @param information
	 * @param cost
	 *            - generated from Cost annotation
	 */
	public CardInformation(String title, String information, String cost,
			CardType type) {
		this.title = title;
		this.information = information;
		this.cost = cost;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public String getInformation() {
		return information;
	}

	public String getCost() {
		return cost;
	}

	public CardType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof CardInformation) {
			return ((CardInformation) obj).getTitle().equals(title);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}

	@Override
	public int compareTo(CardInformation o) {
		if (this.type == o.type) {
			return this.title.compareTo(o.title);
		} else {
			return this.type.compareTo(o.type);
		}
	}

}
