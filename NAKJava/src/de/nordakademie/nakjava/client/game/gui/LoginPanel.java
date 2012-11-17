package de.nordakademie.nakjava.client.game.gui;

import javax.swing.JLabel;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.StatePanel;
import de.nordakademie.nakjava.client.internal.gui.component.TextField;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;

public class LoginPanel extends StatePanel {
	private TextField playerName;

	public LoginPanel(Boolean actor) {
		super(actor);
		add(new JLabel("Name:"));
		playerName = new TextField(TypePlayerNameAction.class,
				VisibleModelFields.LOGINSPECIFICMODEL_CURRENTPARTOFNAME_SELF,
				actor);
		add(playerName);
		if (actor) {
			add(new Button("Login", SubmitPlayerNameAction.class));
		}

	}

	@Override
	public void setName(String name) {
		playerName.setText(name);
	}

	@Override
	public State[] getStates() {
		return new State[] { State.LOGIN };
	}
}
