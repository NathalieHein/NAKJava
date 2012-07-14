package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.Action;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ButtonAction extends ActionContext {

	private String buttonText;
	public static final String X = "x";
	public static final String Y = "y";

	public ButtonAction(String buttonText) {
		super();
		this.buttonText = buttonText;
	}

	@Override
	protected Action getAction() throws RemoteException {
		return new ActionAbstractImpl() {

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
