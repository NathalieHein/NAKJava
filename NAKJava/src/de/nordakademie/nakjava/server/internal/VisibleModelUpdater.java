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
		if (!Model.getInstance().isModeUnique()) {
			for (Player player : Player.getPlayers()) {
				updatePlayerModel(player);
			}
		} else {
			updatePlayerModel(Model.getInstance().getCurrentPlayer());
		}

	}

	private void updatePlayerModel(Player player) {
		player.getState().getModel().setName(Model.getInstance().getName());
	}

}
