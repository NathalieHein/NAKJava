package de.nordakademie.nakjava.client.game.gui;

import javax.swing.JLabel;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.proxy.actions.LeaveGameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.PlayAgainAction;

public class OtherLeftPanel extends Panel {
	public OtherLeftPanel() {
		add(new JLabel("Der andere Spieler hat das Spiel verlassen."));
		add(new Button("Neues Spiel", PlayAgainAction.class));
		add(new Button("Schließen", LeaveGameAction.class));
	}

	@Override
	public State[] getStates() {
		return new State[] { State.OTHERPLAYERLEFTGAME };
	}
}
