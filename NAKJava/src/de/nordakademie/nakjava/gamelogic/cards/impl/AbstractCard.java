package de.nordakademie.nakjava.gamelogic.cards.impl;

/**
 * Cards will be defined with annotations. In State enums vs. annotations and
 * naming conventions
 * 
 * @author Kai
 * 
 */
public abstract class AbstractCard {

	protected AbstractCard() {

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
	public void performAction() {
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
	public boolean checkPrerequirements() {
		return true;
	}

	/**
	 * May be overridden for additional payment
	 */
	public void pay() {

	}

	/**
	 * Pay for playing a card
	 */
	public void payImpl() {
		pay();
	}
}
