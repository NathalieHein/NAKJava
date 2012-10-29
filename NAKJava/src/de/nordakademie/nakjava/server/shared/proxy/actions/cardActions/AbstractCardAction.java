package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public abstract class AbstractCardAction extends ActionContext {

	protected String cardName;

	public AbstractCardAction(String cardName, long batch, long sessionNr) {
		super(batch, sessionNr);
		this.cardName = cardName;
	}

	public String getCardName() {
		return cardName;
	}

}
