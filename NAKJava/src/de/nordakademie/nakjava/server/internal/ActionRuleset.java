package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;

import de.nordakademie.nakjava.server.shared.proxy.Action;

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
			player.getState().setActions(new ArrayList<Action>());
		}
	}
}
