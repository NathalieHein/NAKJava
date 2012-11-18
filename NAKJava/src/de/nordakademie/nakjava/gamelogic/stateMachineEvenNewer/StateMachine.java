package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.AdjustCardhand;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.ConfigureGameAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.GameEndAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.Postaction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.Preaction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.ReadyToStartAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.RoundEndAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.SimulationAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.StateAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.Model;

/**
 * StateMachine to produce change between States and invoke the corresponding
 * action
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public final class StateMachine {
	static StateMachine instance;
	Map<State, StateAction> stateToAction;

	private StateMachine() {
		stateToAction = new HashMap<>();
		initialize();
	}

	public static synchronized StateMachine getInstance() {
		if (instance == null) {
			instance = new StateMachine();
		}
		return instance;
	}

	private void initialize() {
		stateToAction.put(State.CONFIGUREGAME, new ConfigureGameAction());
		stateToAction.put(State.PREACTIONSTATE, new Preaction());
		stateToAction.put(State.POSTACTIONSTATE, new Postaction());
		stateToAction.put(State.ADJUSTCARDHANDSTATE, new AdjustCardhand());
		stateToAction.put(State.SIMULATIONSTATE, new SimulationAction());
		stateToAction.put(State.STOP, new RoundEndAction());
		stateToAction.put(State.READYTOSTARTSTATE, new ReadyToStartAction());
		stateToAction.put(State.ENDOFGAMESTATE, new GameEndAction());
	}

	/**
	 * perform change to next state and invokes the corresponding action that
	 * will update the model
	 * 
	 * @param model
	 *            that is processed
	 */
	public void run(Model model) {
		State nextState = model.getSelf().getState().getFollowUpState();
		model.getSelf().setState(nextState);
		if (stateToAction.containsKey(nextState)) {
			stateToAction.get(nextState).perform(model);
		}
	}

	/**
	 * performs no change of state, just invokes the corresponding action that
	 * will update the model
	 * 
	 * @param model
	 *            that is processed
	 */
	public void runCurrentState(Model model) {
		State state = model.getSelf().getState();
		if (stateToAction.containsKey(state)) {
			stateToAction.get(state).perform(model);
		}
	}
}
