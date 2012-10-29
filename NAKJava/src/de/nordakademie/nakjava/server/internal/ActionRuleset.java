package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionRuleset {

	// Suppresses default constructor for noninstantiability
	private ActionRuleset() {
		throw new AssertionError();
	}

	// TODO generate new Actions according to model + batch - to be
	// implemented

	// TODO will need the sessionNr here -> maybe complete model??
	private static void updatePlayerActions(Player player,
			PlayerState opponent, long batch, long sessionId) {
		PlayerState playerState = player.getGamelogicPlayer();
		List<ActionContext> actions = new ArrayList<>();
		switch (playerState.getState()) {
		case PLAYCARDSTATE:
			Map<Target, PlayerState> map = new HashMap<>();
			map.put(Target.SELF, playerState);
			map.put(Target.OPPONENT, opponent);
			for (String cardName : playerState.getCards().getCardsOnHand()) {
				AbstractCard card = CardLibrary.get().getCards().get(cardName);
				if (card != null && card.checkPrerequirementsImpl(map)) {
					actions.add(new PlayCardAction(cardName, batch, sessionId));
				}
				actions.add(new WithdrawCardAction(cardName, batch, sessionId));
				actions.add(new SimulateCardAction(cardName, batch, sessionId));
			}
			break;
		case ADJUSTCARDHANDSTATE:
			for (String cardName : playerState.getCards().getCardsOnHand()) {
				actions.add(new WithdrawCardAction(cardName, batch, sessionId));
				actions.add(new SimulateCardAction(cardName, batch, sessionId));
			}
			player.getState().setActions(actions);
			break;
		case STOP:
			break;
		case NEXT:
			// TODO only a ServerAction here
			break;
		}

		/*
		 * if (!model.isModeUnique() && !model.isStillRoom() && player !=
		 * model.getCurrentPlayer()) { List<ActionContext> actionContexts = new
		 * ArrayList<>(); actionContexts.add(null);
		 * player.getState().setActions(actionContexts); }
		 */
	}

	public static void update(Model model, long batch) {
		if (!model.isModeUnique()) {

			for (Player player : model.getIterableListOfPlayers()) {
				for (Player otherPlayer : model.getIterableListOfPlayers()) {
					if (player != otherPlayer) {
						updatePlayerActions(player,
								otherPlayer.getGamelogicPlayer(), batch,
								model.getSessionId());
					}
				}
			}

		} else {
			// TODO unique session
			updatePlayerActions(model.getActionInvoker(), null, batch,
					model.getSessionId());
		}
	}
}
