package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.internal.model.SimulationModel;
import de.nordakademie.nakjava.server.internal.model.SimulationModels;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SimulateCardAction extends ActionContext {
	private int[] thresholds;

	public SimulateCardAction(int[] thresholds, long sessionNr) {
		super(sessionNr);
		this.thresholds = thresholds;
	}

	public int[] getThresholds() {
		return thresholds;
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				if (!session.isActionInvokerCurrentPlayer()) {
					session.getModel().changeSelfAndOpponent();
				}
				Model model = session.getModel();
				PlayerState self = model.getSelf();
				InGameSpecificModel specificModel = (InGameSpecificModel) self
						.getStateSpecificModel();
				List<String> allowedCards = new ArrayList<>();
				for (String cardName : specificModel.getCards()
						.getCardsOnHand()) {
					if (CardLibrary
							.get()
							.getCardForName(cardName)
							.checkPrerequirementsImpl(
									model.getSelfOpponentMap())) {
						allowedCards.add(cardName);
					}
				}
				SimulationModels simulationModels = new SimulationModels(model,
						allowedCards, thresholds);
				self.setSimulationModels(simulationModels);
				self.setState(State.SIMULATIONSTATE);
				ExecutorService threadPool = Executors.newFixedThreadPool(5);
				for (SimulationModel simulationModel : simulationModels
						.getSimulationModels()) {
					threadPool
							.execute(new SimulateCardRunnable(simulationModel));
				}
				threadPool.shutdown();
				try {
					threadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				threadPool.shutdownNow();
			}
		};
	}

}
