package de.nordakademie.nakjava.client.bot.geneticBot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileLock;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.nordakademie.nakjava.client.bot.AbstractActionSelector;
import de.nordakademie.nakjava.client.bot.AbstractBotClient;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class GeneticBot extends AbstractBotClient {

	private static final String GENETIC_BOT_KNOWLEDGE_PATH = "geneticBot.knowledge";
	private Map<String, Long> countCards;
	private Map<String, Long> knowledge;

	private int roundsPlayed = 0;
	private String name;

	protected GeneticBot() throws RemoteException {
		super();
	}

	@Override
	public void turn(PlayerState state) {
		System.out.println("Turn");
		List<ActionContext> playCards = AbstractActionSelector.selectActions(
				state.getActions(), new ActionContextSelector() {

					@Override
					public boolean select(ActionContext context) {
						return context instanceof PlayCardAction;
					}
				});

		if (playCards.size() > 0) {
			Long biggest = Long.MIN_VALUE;
			PlayCardAction cardToPlay = null;

			for (ActionContext playAction : playCards) {
				PlayCardAction card = (PlayCardAction) playAction;

				Long count = knowledge.get(card.getCardName());

				if (count != null && biggest < count) {
					cardToPlay = card;
					biggest = count;
				} else if (count == null) {
					cardToPlay = card;
					break;
				}
			}

			countCard(cardToPlay.getCardName());
			cardToPlay.perform();
			System.out.println("Perform Card");

		} else {
			System.out.println("No Cards");
			drop(state);
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

		Long smallest = Long.MAX_VALUE;
		WithdrawCardAction cardToWithDraw = null;

		for (ActionContext card : withDraw) {
			WithdrawCardAction withdrawAction = (WithdrawCardAction) card;

			Long count = knowledge.get(withdrawAction.getCardName());
			if (count != null && smallest > count) {
				cardToWithDraw = withdrawAction;
				smallest = count;
			} else if (cardToWithDraw == null) {
				cardToWithDraw = withdrawAction;
			}
		}

		cardToWithDraw.perform();
	}

	@Override
	public void initBot(PlayerState state) {
		System.out.println("Round started");
		countCards = new HashMap<>();
		name = VisibleModelFields.MODEL_STRATEGY_SELF.getValue(
				state.getModel().getGenericTransfer()).getName();
		File file = new File(GENETIC_BOT_KNOWLEDGE_PATH + name);
		if (!file.exists()) {
			knowledge = new HashMap<>();
			return;
		}

		ObjectInputStream ois = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ois = new ObjectInputStream(fileInputStream);
			knowledge = (Map<String, Long>) ois.readObject();
			if (knowledge == null) {
				knowledge = new HashMap<>();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
			}
		}

	}

	@Override
	public void gameFinished(RoundResult result) {

		roundsPlayed++;
		System.out.println("Roun finished");

		for (Entry<String, Long> card : countCards.entrySet()) {
			Long knowledgeCount = knowledge.get(card.getKey());
			if (knowledgeCount == null) {
				knowledgeCount = (long) 0;
			}
			if (result == RoundResult.LOST) {
				knowledgeCount -= card.getValue();
			} else {
				knowledgeCount += card.getValue();
			}
			knowledge.put(card.getKey(), knowledgeCount);
		}
		File file = new File(GENETIC_BOT_KNOWLEDGE_PATH + name);
		ObjectOutputStream ous = null;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			FileLock lock = fileOutputStream.getChannel().lock();
			ous = new ObjectOutputStream(fileOutputStream);
			ous.writeObject(knowledge);
			lock.release();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ous.close();
			} catch (IOException e) {
			}
		}

	}

	private void countCard(String title) {
		Long count = countCards.get(title);
		if (count == null) {
			count = (long) 0;
		}

		count++;

		countCards.put(title, count);

	}

	public static void main(String[] args) {
		try {
			new GeneticBot(null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
