package de.nordakademie.nakjava.gamelogic.shared.playerstate;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.internal.model.StateSpecificModel;
import de.nordakademie.nakjava.server.internal.model.VisibleField;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;

public class PlayerState {

	@VisibleField(targets = {
			@TargetInState(target = Target.SELF,
					states = { State.LOGIN, State.CONFIGUREGAME,
							State.READYTOSTARTSTATE, State.PLAYCARDSTATE,
							State.ADJUSTCARDHANDSTATE, State.STOP,
							State.EDITDECK }),
			@TargetInState(target = Target.OPPONENT,
					states = { State.LOGIN, State.CONFIGUREGAME,
							State.READYTOSTARTSTATE, State.PLAYCARDSTATE,
							State.ADJUSTCARDHANDSTATE, State.STOP }) })
	private State state;
	private StateSpecificModel stateSpecificModel;

	public PlayerState() {

		state = State.LOGIN;
		stateSpecificModel = new LoginSpecificModel();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public StateSpecificModel getStateSpecificModel() {
		return stateSpecificModel;
	}

	public void setStateSpecificModel(StateSpecificModel stateSpecificModel) {
		this.stateSpecificModel = stateSpecificModel;
	}
}
