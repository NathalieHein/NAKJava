package de.nordakademie.nakjava.client.game.gui;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.Label;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.LeaveGameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.PlayAgainAction;

public class EndOfGamePanel extends Panel {

	public EndOfGamePanel() {
		add(new Label("Du hast ",
				VisibleModelFields.INGAMESPECIFICMODEL_ROUNDRESULT_SELF, "!"));
		add(new Button("Neues Spiel", PlayAgainAction.class));
		add(new Button("Schließen", LeaveGameAction.class));
	}

	@Override
	public State[] getStates() {
		return new State[] { State.ENDOFGAMESTATE };
	}

}
