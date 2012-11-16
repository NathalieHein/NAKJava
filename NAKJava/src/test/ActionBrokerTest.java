package test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;
import de.nordakademie.nakjava.server.shared.proxy.CheckInImpl;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.FinishConfiguringAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class ActionBrokerTest {

	public static void main(String[] args) {
		(new ActionBrokerTest()).test();

	}

	private synchronized void test() {
		startServer();
		try {
			wait(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runClients(50);
	}

	private void runClients(int i) {
		ExecutorService threadPool = Executors.newFixedThreadPool(i);
		for (int j = 0; j < i; j++) {
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					createAndInitializeClient();
				}

			});
		}
	}

	private void createAndInitializeClient() {
		try {
			new Client() {
				private Random random = new Random();

				@Override
				protected void stateChange(PlayerState state) {
					List<ActionContext> actions = state.getActions();
					ActionContext action;
					if (actions.size() > 1) {
						do {
							action = actions
									.get(random.nextInt(actions.size()));
						} while (action instanceof FinishConfiguringAction);
						State opponent = VisibleModelFields.PLAYERSTATE_STATE_OPPONENT
								.getValue(state.getModel().getGenericTransfer());
						State self = VisibleModelFields.PLAYERSTATE_STATE_SELF
								.getValue(state.getModel().getGenericTransfer());
						if (opponent == null) {
							System.out.println(self.toString() + " "
									+ action.getClass());
						} else {
							System.out
									.println(opponent.toString() + " "
											+ self.toString() + " "
											+ action.getClass());
						}
						// for (int i = 0; i < 10; i++) {
						action.perform();
						// }
					}
				}

				@Override
				protected void preCheckin() {

				}

				@Override
				public void remoteClose() {
					// TODO Auto-generated method stub

				}

				@Override
				public void error(String text) {
					// TODO Auto-generated method stub

				}

			};
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startServer() {
		CheckInImpl.load();
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			CheckIn checkIn = new CheckInImpl();

			Naming.rebind("CheckIn", checkIn);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Server started");
	}
}
