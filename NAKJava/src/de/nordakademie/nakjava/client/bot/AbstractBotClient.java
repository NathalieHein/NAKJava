package de.nordakademie.nakjava.client.bot;

import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.AdjustCardhand;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public abstract class AbstractBotClient extends Client {

	private Map<State, BotBehaviour> behaviourLookup;
	private PlayerStateHook hook;
	private State state;

	private AbstractBotClient() throws RemoteException {
		super();
	}

	protected AbstractBotClient(PlayerStateHook hook) throws RemoteException {
		this();
		this.hook = hook;
	}

	@Override
	public void remoteClose() throws RemoteException {
		System.exit(0);

	}

	@Override
	public void error(String text) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void stateChange(PlayerState playerState) {
		if (hook != null) {
			hook.newState(playerState);
		}

		State newState = VisibleModelFields.PLAYERSTATE_STATE_SELF
				.getValue(playerState.getModel().getGenericTransfer());

		if (state == State.READYTOSTARTSTATE
				&& (newState == State.PLAYCARDSTATE || newState == State.STOP)) {
			initBot(playerState);
		}

		if (newState == State.ENDOFGAMESTATE && state != State.ENDOFGAMESTATE) {
			gameFinished(VisibleModelFields.INGAMESPECIFICMODEL_ROUNDRESULT_SELF
					.getValue(playerState.getModel().getGenericTransfer()));
		}

		if (newState == State.PLAYCARDSTATE) {
			turn(playerState);
		}

		if (newState == State.ADJUSTCARDHANDSTATE) {
			drop(playerState);
		}

		state = newState;

		BotBehaviour behaviour = behaviourLookup.get(newState);
		if (behaviour != null) {
			behaviour.act(playerState);
		}
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

}
