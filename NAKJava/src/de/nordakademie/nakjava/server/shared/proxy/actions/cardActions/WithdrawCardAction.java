package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class WithdrawCardAction extends AbstractCardAction {

	public WithdrawCardAction(String cardName, long batch, long sessionNr) {
		super(cardName, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Model model) {
				Map<Target, PlayerState> map = new HashMap<>();

				for (Player player : model.getIterableListOfPlayers()) {
					if (player == model.getActionInvoker()) {
						map.put(Target.SELF, player.getGamelogicPlayer());
					} else {
						map.put(Target.OPPONENT, player.getGamelogicPlayer());
					}
				}

				if (!map.get(Target.SELF).getCards()
						.discardCardFromHand(cardName)) {
					throw new IllegalStateException(
							"Card to be discarded is not in cardhand");
				}
				StateMachine.run(map);
			}
		};
	}

}
