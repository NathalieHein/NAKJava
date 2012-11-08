package de.nordakademie.nakjava.client.game;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.game.gui.LoginPanel;
import de.nordakademie.nakjava.client.internal.gui.AbstractGUIClient;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

public class Client extends AbstractGUIClient {

	private State oldState;

	protected Client() throws RemoteException {
		super();
	}

	@Override
	public void playerModelChanged(PlayerModel model) {
		State state = model.getTargetToState().get(Target.SELF);

		if (oldState != state) {
			switchPanel(state);
		}

		switch (state) {
		case LOGIN:
			// TODO Loginname needs to be set to panel
			LoginPanel panel = (LoginPanel) getFrame().getContentPane()
					.getComponents()[0];
			panel.setName(model.getTargetToName().get(Target.SELF));
			break;

		default:
			break;
		}

	}

	private void switchPanel(State state) {
		switch (state) {
		case LOGIN:
			updateFrame(new Runnable() {
				@Override
				public void run() {
					getFrame().add(new LoginPanel());
				}
			});
			break;

		default:
			break;
		}

		oldState = state;
	}

	public static void main(String[] args) {
		try {
			new Client();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
