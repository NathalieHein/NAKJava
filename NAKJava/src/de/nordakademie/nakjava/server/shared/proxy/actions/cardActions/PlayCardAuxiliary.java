package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

public class PlayCardAuxiliary {

	public static void playCard(Model model, String cardName) {
		AbstractCard card = CardLibrary.get().getCards().get(cardName);
		if (card != null) {
			model.setLastPlayedCard(cardName);
			PlayerState self = model.getSelf();
			self.setState(State.ADJUSTCARDHANDSTATE);
			Map<Target, PlayerState> selfOpponentMap = model
					.getSelfOpponentMap();
			card.payImpl(selfOpponentMap);
			card.performActionImpl(selfOpponentMap);
			InGameSpecificModel selfSpecificModel = (InGameSpecificModel) self
					.getStateSpecificModel();
			if (!selfSpecificModel.getCards().discardCardFromHand(cardName)) {
				throw new IllegalStateException(
						"Card to be discarded is not in cardhand");
			}
		} else {
			// TODO what if card not found
			// --> should be nothing because should go back to trigger
			// ActionRuleSet
		}
	}

	public static void checkEndOfGame(Model model) {
		PlayerState self = model.getSelf();
		PlayerState opponent = model.getOpponent();
		InGameSpecificModel selfSpecificModel = (InGameSpecificModel) self
				.getStateSpecificModel();
		InGameSpecificModel opponetSpecificModel = null;
		if (opponent.getState() == State.STOP) {
			opponetSpecificModel = (InGameSpecificModel) opponent
					.getStateSpecificModel();
		} else {
			// TODO Fehlerbehandlung
			System.out.println("wrong state of opponent in playCard");
		}
		WinStrategy winStrategy = WinStrategies.getInstance()
				.getStrategyForName(model.getStrategy());
		Map<Target, RoundResult> roundResult = winStrategy.getRoundResult(model
				.getSelfOpponentMap());
		RoundResult selfRoundResult = roundResult.get(Target.SELF);
		if (selfRoundResult != RoundResult.NEUTRAL) {
			self.setState(State.ENDOFGAMESTATE);
			opponent.setState(State.ENDOFGAMESTATE);

			selfSpecificModel.setRoundResult(selfRoundResult);
			opponetSpecificModel.setRoundResult(roundResult
					.get(Target.OPPONENT));
		} else {
			StateMachine.getInstance().runCurrentState(model);
		}
		// if card states: "Play again" than state must be set to
		// "PREACTION"
	}
}
