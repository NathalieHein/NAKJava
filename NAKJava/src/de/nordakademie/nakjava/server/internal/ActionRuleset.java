package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SaveDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.CreateNewDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.EditDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectWinStrategy;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionRuleset {
	private static final char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '\n', '\b' };

	// Suppresses default constructor for noninstantiability
	private ActionRuleset() {
		throw new AssertionError();
	}

	// TODO generate new Actions according to model + batch - to be
	// implemented

	// TODO will need the sessionNr here -> maybe complete model??
	private static void updatePlayerActions(Player player, PlayerState self,
			PlayerState opponent, long batch, long sessionNr) {
		List<ActionContext> actions = new ArrayList<>();
		// TODO modeUnique via playerStates to be added: if player ==
		// actionInvoker for those states
		switch (self.getState()) {
		case LOGIN:
			for (char letter : letters) {
				actions.add(new TypePlayerNameAction(letter, batch, sessionNr));
			}
			if (player.getName() != null) {
				actions.add(new SubmitPlayerNameAction(batch, sessionNr));
			}
			break;
		case CHOOSEDECK:
			for (String savedDeckName : player.getSavedDecks().keySet()) {
				actions.add(new EditDeckAction(savedDeckName, batch, sessionNr));
				actions.add(new SelectDeckAction(savedDeckName, batch,
						sessionNr));
			}
			actions.add(new CreateNewDeckAction(batch, sessionNr));
			break;
		case CHOOSESTRATEGY:
			for (String winstrategy : WinStrategies.getInstance()
					.getStrategies()) {
				actions.add(new SelectWinStrategy(winstrategy, batch, sessionNr));
			}
		case EDITDECK:
			for (char letter : letters) {
				actions.add(new TypeDeckNameAction(letter, batch, sessionNr));
			}
			// if(!player.getSavedDecks().containsKey(currentDeckName){
			actions.add(new SaveDeckAction(batch, sessionNr));
			// }
			actions.add(new DiscardDeckEditAction(batch, sessionNr));
			break;
		case PLAYCARDSTATE:
			// TODO put model.map in here instead
			Map<Target, PlayerState> map = new HashMap<>();
			map.put(Target.SELF, self);
			map.put(Target.OPPONENT, opponent);
			for (String cardName : self.getCards().getCardsOnHand()) {
				AbstractCard card = CardLibrary.get().getCards().get(cardName);
				if (card != null && card.checkPrerequirementsImpl(map)) {
					actions.add(new PlayCardAction(cardName, batch, sessionNr));
					actions.add(new SimulateCardAction(cardName, batch,
							sessionNr));
				}
				actions.add(new WithdrawCardAction(cardName, batch, sessionNr));
			}
			break;
		case ADJUSTCARDHANDSTATE:
			for (String cardName : self.getCards().getCardsOnHand()) {
				actions.add(new WithdrawCardAction(cardName, batch, sessionNr));
			}
			player.getState().setActions(actions);
			break;
		}
	}

	public static void update(Session session, long batch) {

		for (Player player : session.getSetOfPlayers()) {
			for (Player otherPlayer : session.getSetOfPlayers()) {
				if (player != otherPlayer) {
					updatePlayerActions(player, session.getPlayerToStateMap()
							.get(player),
							session.getPlayerToStateMap().get(otherPlayer),
							batch, session.getSessionId());
				}
			}
		}
	}
}
