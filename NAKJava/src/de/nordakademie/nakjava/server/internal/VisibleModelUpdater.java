package de.nordakademie.nakjava.server.internal;

public class VisibleModelUpdater {
	private static VisibleModelUpdater instance;

	private VisibleModelUpdater() {

	}

	public static VisibleModelUpdater getInstance() {
		if (instance == null) {
			instance = new VisibleModelUpdater();
		}
		return instance;
	}

	public void update(long batch) {
		for (Player player : Player.getPlayers()) {
			player.getState().getModel().setName(Model.getInstance().getName());
		}

	}

}
