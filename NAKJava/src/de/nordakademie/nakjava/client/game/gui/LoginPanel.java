package de.nordakademie.nakjava.client.game.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.TextField;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;

public class LoginPanel extends JPanel {
	private TextField playerName;

	public LoginPanel() {
		super();
		add(new JLabel("Name:"));
		playerName = new TextField(TypePlayerNameAction.class);
		add(playerName);
		add(new Button("Login", SubmitPlayerNameAction.class));
	}

	@Override
	public void setName(String name) {
		playerName.setText(name);
	}
}
