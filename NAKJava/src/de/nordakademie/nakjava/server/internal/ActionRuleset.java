package de.nordakademie.nakjava.server.internal;

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

	}
}
