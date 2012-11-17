package test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

import de.nordakademie.nakjava.client.internal.AbstractClient;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.LeaveGameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class NoCardRuleTest {
	public static void main(String[] args) {
		try {
			new AbstractClient() {
				private Random random = new Random();

				@Override
				public void remoteClose() throws RemoteException {
					// TODO Auto-generated method stub

				}

				@Override
				public void error(String text) throws RemoteException {
					// TODO Auto-generated method stub

				}

				@Override
				protected synchronized void stateChange(PlayerState state) {
					List<ActionContext> actions = state.getActions();
					ActionContext action;
					if (actions.size() > 1) {
						do {
							action = actions
									.get(random.nextInt(actions.size()));
						} while (action instanceof LeaveGameAction);
						State opponent = VisibleModelFields.PLAYERSTATE_STATE_OPPONENT
								.getValue(state.getModel().getGenericTransfer());
						State self = VisibleModelFields.PLAYERSTATE_STATE_SELF
								.getValue(state.getModel().getGenericTransfer());
						List<Artifact> artifacts = VisibleModelFields.INGAMESPECIFICMODEL_ARTIFACTS_SELF
								.getValue(state.getModel().getGenericTransfer());
						if (opponent == null) {
							System.out.println(self.toString() + " ");
						} else {
							System.out.println(opponent.toString() + " "
									+ self.toString() + " ");
						}
						if (artifacts != null) {
							for (Artifact string : artifacts) {
								System.out.print(string.getClass().toString()
										+ string.getCount());
							}
							System.out.println();
						}
						// for (int i = 0; i < 10; i++) {
						action.perform();
						// }
					}
				}

				@Override
				protected void preCheckin() {
					// TODO Auto-generated method stub

				}
			};
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
