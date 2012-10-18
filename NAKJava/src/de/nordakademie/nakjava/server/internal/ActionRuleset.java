package de.nordakademie.nakjava.server.internal;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.proxy.actions.ButtonAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.KeyAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionRuleset {
	private static ActionRuleset instance;

	private ActionRuleset() {
	}

	public static ActionRuleset getInstance() {
		if (instance == null) {
			instance = new ActionRuleset();
		}
		return instance;
	}

	public void update(long batch) {
		if (!Model.getInstance().isModeUnique()) {
			for (Player player : Player.getPlayers()) {
				updateActions(batch, player);
			}
		} else {
			updateActions(batch, Model.getInstance().getCurrentPlayer());
		}
	}

	private void updateActions(long batch, Player player) {
		List<ActionContext> actions = new ArrayList<>();

		for (int i = 1; i <= 10000; i++) {
			try {
				actions.add(new KeyAction(i, batch));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		Model model = Model.getInstance();
		if (model.isX() && model.isY()) {
			// Spiel verloren
		} else if (model.isX()) {
			actions.add(new ButtonAction(ButtonAction.Y, batch));
		} else {
			actions.add(new ButtonAction(ButtonAction.X, batch));
			actions.add(new ButtonAction(ButtonAction.Y, batch));
		}

		player.getState().setActions(actions);
	}
}
