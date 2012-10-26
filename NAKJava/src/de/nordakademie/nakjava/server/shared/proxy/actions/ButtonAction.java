package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ButtonAction extends ActionContext {

	private String buttonText;
	public static final String X = "x";
	public static final String Y = "y";

	public ButtonAction(String buttonText, long batch, long sessionNr) {
		super(batch, sessionNr);
		this.buttonText = buttonText;
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Model model) {
				if (buttonText.equals(X)) {
					model.setX(true);
					model.setY(!model.isY());
				} else {
					model.setY(true);
					model.setX(!model.isX());
				}
			}
		};
	}
}
