package de.nordakademie.nakjava.server.internal;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

	public void update() {

		for (Player player : Player.getPlayers()) {
			List<ActionContext> actions = new ArrayList<>();

			for (int i = KeyEvent.VK_A; i <= KeyEvent.VK_Z; i++) {
				try {
					actions.add(new KeyAction(i));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			player.getState().setActions(actions);
		}
	}
}
