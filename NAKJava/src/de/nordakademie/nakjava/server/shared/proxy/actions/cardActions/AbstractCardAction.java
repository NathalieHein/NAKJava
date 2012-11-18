package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Abstract class for all SeverActions that contain a cardName as variable
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class AbstractCardAction extends ActionContext {

	protected String cardName;

	public AbstractCardAction(String cardName, long sessionNr) {
		super(sessionNr);
		this.cardName = cardName;
	}

	public String getCardName() {
		return cardName;
	}

}
