package de.nordakademie.nakjava.client.game;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.internal.gui.AbstractGUIClient;

public class Client extends AbstractGUIClient {

	protected Client() throws RemoteException {
		super();
	}

	// @Override
	// public void playerModelChanged(PlayerModel model) {
	// State state = model.getTargetToState().get(Target.SELF);
	//
	// if (oldState != state) {
	// switchPanel(state);
	// }
	//
	// switch (state) {
	// case LOGIN:
	// // TODO Loginname needs to be set to panel
	// LoginPanel loginPanel = (LoginPanel) getFrame().getContentPane()
	// .getComponents()[0];
	// loginPanel.setName(model.getTargetToName().get(Target.SELF));
	// break;
	// case CONFIGUREGAME:
	// ConfigurationPanel configPanel = (ConfigurationPanel) getFrame()
	// .getContentPane().getComponents()[0];
	// configPanel
	// .setCurrentCardDeck(((ConfigurationSpecificInformation) model
	// .getStateSpecificInfos())
	// .getCurrentlyChosenCardDeck());
	// configPanel
	// .setCurrentWinStrategy(((ConfigurationSpecificInformation) model
	// .getStateSpecificInfos())
	// .getCurrentlyChosenWinStrategy());
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	//
	// private void switchPanel(State state) {
	// switch (state) {
	// case LOGIN:
	// updateFrame(new Runnable() {
	// @Override
	// public void run() {
	// getFrame().add(new LoginPanel());
	// }
	// });
	// break;
	// case CONFIGUREGAME:
	// updateFrame(new Runnable() {
	//
	// @Override
	// public void run() {
	// getFrame().add(new ConfigurationPanel());
	// }
	// });
	// break;
	//
	// default:
	// break;
	// }
	//
	// oldState = state;
	// }

	public static void main(String[] args) {
		try {
			new Client();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void remoteClose() {
		System.exit(0);
	}

}
