package de.nordakademie.nakjava.client.bot.genericbot;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.client.bot.AbstractActionSelector;
import de.nordakademie.nakjava.client.bot.AbstractBotClient;
import de.nordakademie.nakjava.client.bot.genericbot.transformers.CheckTransformer;
import de.nordakademie.nakjava.client.bot.genericbot.transformers.WinCheckMeasurement;
import de.nordakademie.nakjava.client.bot.genericbot.transformers.WinCheckTransformer;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.GUIHook;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategyInformation;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.FinishSimulationAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;
import de.nordakademie.nakjava.server.shared.serial.VisibleSimulationModel;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

/**
 * Generic bot which calculates its strategy by transforming {@link WinStrategy}
 * . This bot has a positive card playing habit. It tries to avoid dropping
 * cards and chooses always the best suiting card. The best card is not obtained
 * by parsing the card, it is done by simulating the card for the future. This
 * decouples the bot from the cards.
 * 
 */
public class GenericBot extends AbstractBotClient {

	private List<VisibleSimulationModel> simulation;
	private Map<Class<? extends WinCheck>, WinCheckTransformer> transformers;
	private List<WinCheckMeasurement> measurements;

	private int simulationLength;

	private Map<String, SimulationCardResult> simulationResult;

	/**
	 * Creates the GenericBot
	 * 
	 * @param simulationLength
	 *            how far should be simulated
	 * @param gui
	 *            can be passed to the bot for control purposes
	 * @param showStatistic
	 *            shows a statistic window
	 * @throws RemoteException
	 */
	protected GenericBot(int simulationLength, GUIHook gui,
			boolean showStatistic) throws RemoteException {
		super(gui, showStatistic);

		this.simulationLength = simulationLength;
		transformers = new HashMap<>();

		List<Class<?>> classes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.client.bot.genericbot.transformers",
				"", new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class clazz) {
						return clazz
								.isAnnotationPresent(CheckTransformer.class);
					}
				});

		for (Class<?> clazz : classes) {
			CheckTransformer transformer = clazz
					.getAnnotation(CheckTransformer.class);
			try {
				transformers.put(transformer.transformTarget(),
						(WinCheckTransformer) clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void stateChange(PlayerState playerState) {
		super.stateChange(playerState);

		State state = VisibleModelFields.PLAYERSTATE_STATE_SELF
				.getValue(playerState.getModel().getGenericTransfer());
		if (state == State.SIMULATIONSTATE) {
			simulation = VisibleModelFields.SIMULATIONMODELS_SIMULATIONMODELS_SELF
					.getValue(playerState.getModel().getGenericTransfer());

			AbstractActionSelector.selectAction(playerState.getActions(),
					new ActionContextSelector() {

						@Override
						public boolean select(ActionContext context) {
							return context instanceof FinishSimulationAction;

						}
					}).perform();
		}

	}

	@Override
	public void turn(PlayerState state) {
		if (simulation == null) {
			AbstractActionSelector.selectAction(state.getActions(),
					new ActionContextSelector() {

						@Override
						public boolean select(ActionContext context) {
							return context instanceof SimulateCardAction;

						}
					}).perform();
		} else {

			simulationResult = new HashMap<>();

			for (VisibleSimulationModel vsm : simulation) {
				SimulationCardResult result = simulationResult.get(vsm
						.getSimulatedCard());
				if (result == null) {
					result = new SimulationCardResult(vsm.getSimulatedCard());
					simulationResult.put(vsm.getSimulatedCard(), result);
				}
				for (WinCheckMeasurement measurement : measurements) {
					result.putResult(vsm.getToBeSimulatedNumberOfRounds(),
							measurement.measure(vsm.getTargetToArtifacts()));
				}

			}

			double highest = 0;
			String card = "";

			for (SimulationCardResult result : simulationResult.values()) {
				Double res = result.getResult(simulationLength);
				if (res > highest) {
					highest = res;
					card = result.cardName;
				}
			}

			if (card.equals("")) {
				drop(state);
			} else {
				play(card, state);
			}

			simulation = null;
		}

	}

	@Override
	public void drop(PlayerState state) {
		List<ActionContext> withDraw = AbstractActionSelector.selectActions(
				state.getActions(), new ActionContextSelector() {

					@Override
					public boolean select(ActionContext context) {
						return context instanceof WithdrawCardAction;
					}
				});

		String withDrawCard = "";
		Double smallest = 1.0;

		for (ActionContext context : withDraw) {
			WithdrawCardAction withdraw = (WithdrawCardAction) context;
			SimulationCardResult result = simulationResult.get(withdraw
					.getCardName());
			if (result == null) {
				withDrawCard = withdraw.getCardName();
			} else {
				Double res = result.getResult(simulationLength);

				if (smallest > res) {
					smallest = res;
					withDrawCard = result.cardName;
				}
			}

		}

		final String toWithDraw = withDrawCard;

		AbstractActionSelector.selectAction(state.getActions(),
				new ActionContextSelector() {

					@Override
					public boolean select(ActionContext context) {
						if (context instanceof WithdrawCardAction) {
							WithdrawCardAction card = (WithdrawCardAction) context;
							return card.getCardName().equals(toWithDraw);
						}
						return false;
					}
				}).perform();

	}

	private void play(final String card, PlayerState state) {
		AbstractActionSelector.selectAction(state.getActions(),
				new ActionContextSelector() {

					@Override
					public boolean select(ActionContext context) {
						if (context instanceof PlayCardAction) {
							PlayCardAction cardAction = (PlayCardAction) context;
							return cardAction.getCardName().equals(card);
						}

						return false;
					}
				}).perform();
	}

	@Override
	public void initBot(PlayerState state) {
		measurements = new LinkedList<>();

		WinStrategyInformation wsi = VisibleModelFields.MODEL_STRATEGY_SELF
				.getValue(state.getModel().getGenericTransfer());
		for (WinCheck check : wsi.getWinChecks()) {
			WinCheckTransformer transformer = transformers
					.get(check.getClass());
			if (transformer != null) {
				measurements.add(transformer.transform(check));
			}
		}
	}

	@Override
	public void gameFinished(RoundResult result) {
	}

	/**
	 * Bean for organizing simulation card results. It stores a cardName and the
	 * specific future round results.
	 * 
	 */
	private class SimulationCardResult {
		private String cardName;
		private Map<Integer, Double> simulationResult = new HashMap<>();

		public SimulationCardResult(String name) {
			this.cardName = name;
		}

		/**
		 * Adds a result for a given round
		 * 
		 * @param round
		 * @param result
		 */
		public void putResult(int round, double result) {
			Double res = simulationResult.get(round);
			if (res == null || res < result) {
				simulationResult.put(round, result);
			}

		}

		public Double getResult(int round) {
			return simulationResult.get(round);
		}

	}

	/**
	 * Starter for a headless generic bot
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new GenericBot(10, null, true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
