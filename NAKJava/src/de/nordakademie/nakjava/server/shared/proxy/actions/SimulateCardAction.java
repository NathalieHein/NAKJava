package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SimulateCardAction extends ActionContext {

	private String cardName;

	protected SimulateCardAction(String cardName, long batch) {
		super(batch);
		this.cardName = cardName;
	}

	public String getCardName() {
		return cardName;
	}

	@Override
	protected ServerAction getAction() throws RemoteException {
		return new ActionAbstractImpl() {

			@Override
			protected void performImpl(Model model) {
				// TODO Auto-generated method stub

			}
		};
	}

}
