package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.internal.model.SimulationModel;
import de.nordakademie.nakjava.server.internal.model.SimulationModels;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.shared.proxy.CheckInImpl;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardRunnable;

public class SimulationTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Method method;
		try {
			method = CheckInImpl.class.getDeclaredMethod("load", null);
			method.setAccessible(true);
			method.invoke(CheckInImpl.class, null);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Player player1 = new Player(null, null);
		Player player2 = new Player(null, null);
		Session session = new Session(player1, 1);
		session.addPlayer(player2);
		Model model = session.getModel();
		PlayerState self = model.getSelf();
		PlayerState opponent = model.getOpponent();
		self.setState(State.CONFIGUREGAME);
		self.setStateSpecificModel(new ConfigureGameSpecificModel(new Deck(
				"StandardDeck", CardLibrary.get().getCards().keySet())));
		opponent.setState(State.CONFIGUREGAME);
		opponent.setStateSpecificModel(new ConfigureGameSpecificModel(new Deck(
				"StandardDeck", CardLibrary.get().getCards().keySet())));
		StateMachine.getInstance().run(model);
		model.changeSelfAndOpponent();
		StateMachine.getInstance().run(model);
		int[] thresholds = { 1, 5, 10 };
		List<? extends Artifact> preArtifacts = ((InGameSpecificModel) self
				.getStateSpecificModel()).getArtifacts();
		performImpl(session, thresholds);
		List<? extends Artifact> postArtifacts = ((InGameSpecificModel) self
				.getStateSpecificModel()).getArtifacts();
		SimulationModels simModels = self.getSimulationModels();
	}

	private static void performImpl(Session session, int[] thresholds) {
		if (!session.isActionInvokerCurrentPlayer()) {
			session.getModel().changeSelfAndOpponent();
		}
		Model model = session.getModel();
		PlayerState self = model.getSelf();
		InGameSpecificModel specificModel = (InGameSpecificModel) self
				.getStateSpecificModel();
		List<String> allowedCards = new ArrayList<>();
		for (String cardName : specificModel.getCards().getCardsOnHand()) {
			if (CardLibrary.get().getCardForName(cardName)
					.checkPrerequirementsImpl(model.getSelfOpponentMap())) {
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
			threadPool.execute(new SimulateCardRunnable(simulationModel));
		}
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadPool.shutdownNow();
	}
}
