package de.nordakademie.nakjava.client.game.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.client.internal.gui.component.TextField;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;

public class LoginPanel extends Panel {
	private TextField playerName;

	public LoginPanel() {
		super();
		add(new JLabel("Name:"));
		playerName = new TextField(TypePlayerNameAction.class,
				VisibleModelFields.LOGINSPECIFICMODEL_CURRENTPARTOFNAME_SELF);
		add(playerName);
		add(new Button("Login", SubmitPlayerNameAction.class));
	}

	@Override
	public void setName(String name) {
		playerName.setText(name);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new LoginPanel());
		System.out.println(frame.getComponents().length);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public State[] getStates() {
		return new State[] { State.LOGIN };
	}
}
