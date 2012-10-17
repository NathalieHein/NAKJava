package de.nordakademie.nakjava.gamelogic.cards.impl;

/**
 * Cards will be defined with annotations. In State enums vs. annotations and
 * naming conventions
 * 
 * @author Kai
 * 
 */
public abstract class AbstractCard {

	private Card annotation;

	protected AbstractCard() {
		annotation = getClass().getAnnotation(Card.class);
	}

	/**
	 * Generated from annotations
	 */
	public final void performActionImpl() {
		performAction();
	}

	/**
	 * May be overridden for additional Actions
	 */
	private void performAction() {
	}

	/**
	 * Checks, whether a card could be played or not. Generated from annotations
	 * 
	 * @return
	 */
	public final boolean checkPrerequirementsImpl() {
		return checkPrerequirements() && true;
	}

	/**
	 * May be overridden for additional actions
	 * 
	 * @return
	 */
	private boolean checkPrerequirements() {
		return true;
	}

	/**
	 * Pay for playing a card
	 */
	public void payImpl() {
	}

	public int getTotalCosts() {
		int result = 0;

		for (Cost cost : annotation.costs()) {
			result += cost.amount();
		}

		return result;
	}
}
