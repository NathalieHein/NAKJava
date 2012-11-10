package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

public class ReadyToStartAction implements StateAction {

	@Override
	public void perform(Model model) {
		PlayerState self = model.getSelf();
		PlayerState opponent = model.getOpponent();
		ConfigureGameSpecificModel specificModel = (ConfigureGameSpecificModel) self
				.getStateSpecificModel();
		self.setCards(specificModel.getChosenDeck());
		if (opponent.getState() == State.READYTOSTARTSTATE) {
			opponent.setState(State.STOP);
			self.setStateSpecificModel(null);
			StateMachine.getInstance().run(model);
		} else {
			// only the first player to go into State.READYTOSTARTSTATE sets the
			// WinStrategy
			model.setStrategy(WinStrategies.getInstance().getStrategyForName(
					specificModel.getWinStrategy()));
			self.setStateSpecificModel(null);
		}
	}

}
