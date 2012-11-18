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

public class StateMachine {
	static StateMachine instance;
	Map<State, StateAction> stateToAction;

	// TODO can the statemachine work without getting the model but other parts,
	// otherwise needs to be an object and needs to pass around reference to
	// itself
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

	public void run(Model model) {
		State nextState = model.getSelf().getState().getFollowUpState();
		model.getSelf().setState(nextState);
		if (stateToAction.containsKey(nextState)) {
			stateToAction.get(nextState).perform(model);
		}
	}

	public void runCurrentState(Model model) {
		State state = model.getSelf().getState();
		if (stateToAction.containsKey(state)) {
			stateToAction.get(state).perform(model);
		}
	}
}
