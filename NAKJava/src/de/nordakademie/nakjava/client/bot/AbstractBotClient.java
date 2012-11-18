package de.nordakademie.nakjava.client.bot;

import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.nordakademie.nakjava.client.internal.AbstractClient;
import de.nordakademie.nakjava.client.internal.gui.GUIHook;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.AdjustCardhand;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

/**
 * Abstract base class for bots. Bots may subclass AbstractbotClient and do not
 * need to deal with login, configuration etc. Only methods for: - Dropping a
 * card if needed outside a turn - playing a card - initializing the bot before
 * game - finishing the game with given result (win/lost)
 * 
 */
public abstract class AbstractBotClient extends AbstractClient {

	private Map<State, BotBehaviour> behaviourLookup;
	private GUIHook hook;
	private State state;

	private int roundsPlayed = 0;
	private int win = 0;
	private int lost = 0;

	private BotStatistic statistic;

	/**
	 * Subclasses can extend this constructor
	 * 
	 * @param gui
	 *            GUI Hook for showing details
	 * @param showStatistic
	 *            show the statistic of played, win, lost games
	 * @throws RemoteException
	 */
	protected AbstractBotClient(GUIHook gui, boolean showStatistic)
			throws RemoteException {
		super(gui);
		if (showStatistic) {
			statistic = new BotStatistic();
		}
	}

	@Override
	public void remoteClose() throws RemoteException {
		System.exit(0);

	}

	@Override
	public void error(String text) throws RemoteException {
	}

	/**
	 * Normally bots are programmed with behaviour which describe what to do,
	 * when the bot is in a certain state. Here changes in states are recorded
	 * (and methods invoked) and the behaviours are triggered.
	 */
	@Override
	protected void stateChange(PlayerState playerState) {

		if (hook != null) {
			hook.newState(playerState);
		}

		State newState = VisibleModelFields.PLAYERSTATE_STATE_SELF
				.getValue(playerState.getModel().getGenericTransfer());

		if ((state == State.READYTOSTARTSTATE && newState == State.PLAYCARDSTATE)
				|| (state == State.CONFIGUREGAME && newState == State.STOP)
				|| (state == State.CONFIGUREGAME && newState == State.PLAYCARDSTATE)) {
			initBot(playerState);
		}

		if (newState == State.ENDOFGAMESTATE && state != State.ENDOFGAMESTATE) {
			RoundResult result = VisibleModelFields.INGAMESPECIFICMODEL_ROUNDRESULT_SELF
					.getValue(playerState.getModel().getGenericTransfer());

			roundsPlayed++;
			if (result == RoundResult.WIN) {
				win++;
			} else if (result == RoundResult.LOST) {
				lost++;
			}
			if (statistic != null) {
				statistic.updateData();
			}

			gameFinished(result);
		}

		if (newState == State.PLAYCARDSTATE) {
			turn(playerState);
		}

		if (newState == State.ADJUSTCARDHANDSTATE
				|| newState == State.DISCARDONECARDSTATE) {
			drop(playerState);
		}

		state = newState;

		BotBehaviour behaviour = behaviourLookup.get(newState);
		if (behaviour != null) {
			behaviour.act(playerState);
		}
	}

	/**
	 * Set a GUIHook while running
	 * 
	 * @param hook
	 */
	public void setHook(GUIHook hook) {
		this.hook = hook;
	}

	/**
	 * Subclasses may override this method in order to do something before a
	 * connection to the server is established.
	 */
	@Override
	protected void preCheckin() {
		List<Class<BotBehaviour>> classes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.client.bot", "", BotBehaviour.class);
		behaviourLookup = new EnumMap<>(State.class);
		for (Class<BotBehaviour> clazz : classes) {
			try {
				if (!clazz.isInterface()
						&& !Modifier.isAbstract(clazz.getModifiers())) {
					BotBehaviour instance = clazz.newInstance();
					for (State state : instance.getStates()) {
						behaviourLookup.put(state, instance);
					}
				}

			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException(
						"BotBehaviours must have a public constructor without arguments.");
			}
		}
	}

	/**
	 * It is the bot's turn here. It can decide whether to play or drop a card
	 * 
	 * @param state
	 *            the current state - visible to the player
	 */
	public abstract void turn(PlayerState state);

	/**
	 * If a card has to be dropped in the State {@link AdjustCardhand} then this
	 * chooses a card
	 * 
	 * @param state
	 *            the current state - visible to the player
	 */
	public abstract void drop(PlayerState state);

	/**
	 * This method is called every time before a game starts
	 */
	public abstract void initBot(PlayerState state);

	/**
	 * This method is called, when the game is finished. It is also called with
	 * a neutral Result when the game has finished abnormally. (e.g. other
	 * player left game)
	 * 
	 * @param result
	 */
	public abstract void gameFinished(RoundResult result);

	/**
	 * GUI representation for the statistic of the bot.
	 * 
	 */
	private class BotStatistic extends JFrame {

		JLabel winLabel;
		JLabel lostLabel;
		JLabel gamesPlayed;

		public BotStatistic() {
			setTitle("Statistik");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			winLabel = new JLabel("Gewonnen: " + win);
			lostLabel = new JLabel("Verloren: " + lost);
			gamesPlayed = new JLabel("Gesamt: " + roundsPlayed);
			JPanel panel = new JPanel();
			panel.add(winLabel);
			panel.add(lostLabel);
			panel.add(gamesPlayed);
			add(panel);
			setResizable(false);
			setVisible(true);
			pack();
		}

		/**
		 * Draw the latest values on the GUI
		 */
		public void updateData() {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					winLabel.setText("Gewonnen: " + win);
					lostLabel.setText("Verloren: " + lost);
					gamesPlayed.setText("Gesamt: " + roundsPlayed);
					BotStatistic.this.pack();
				}
			});
		}
	}

}
