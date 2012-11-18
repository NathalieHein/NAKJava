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

/**
 * Utility-class for playing cards
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public final class PlayCardAuxiliary {
	private PlayCardAuxiliary() {

	}

	/**
	 * plays the card: pays the card's costs + performs the card-actions
	 * 
	 * @param model
	 *            : the model to be processed
	 * @param cardName
	 *            : name of card that was played
	 */
	public static void playCard(Model model, String cardName) {
		AbstractCard card = CardLibrary.get().getCards().get(cardName);
		if (card != null) {
			model.setLastPlayedCard(cardName);
			PlayerState self = model.getSelf();
			self.setState(State.ADJUSTCARDHANDSTATE);
			Map<Target, PlayerState> selfOpponentMap = model
					.getSelfOpponentMap();
			InGameSpecificModel selfSpecificModel = (InGameSpecificModel) self
					.getStateSpecificModel();
			if (!selfSpecificModel.getCards().discardCardFromHand(cardName)) {
				throw new IllegalStateException(
						"Card to be discarded is not in cardhand");
			}
			card.payImpl(selfOpponentMap);
			card.performActionImpl(selfOpponentMap);
		}

	}

	/**
	 * checks if either has won and sets both players to endofgame-state
	 * 
	 * @param model
	 *            : the model to be processed
	 */
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
			throw new IllegalStateException(
					"wrong state of opponent in playCard");
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
	}
}
