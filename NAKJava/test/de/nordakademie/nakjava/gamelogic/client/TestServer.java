package de.nordakademie.nakjava.gamelogic.client;

import java.rmi.RemoteException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.CheckInImpl;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.CreateNewDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.FinishConfiguringAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class TestServer {

	private static TestClient client1;
	private static TestClient client2;
	private static TestBot bot1;
	private static TestBot bot2;

	@BeforeClass
	public static void init() {

		CheckInImpl.main(new String[] {});
		try {
			client1 = new TestClient(null);
			Thread.sleep(1000);
			client2 = new TestClient(null);
			Thread.sleep(1000);
			bot1 = new TestBot();
			Thread.sleep(1000);
			bot2 = new TestBot();
			Thread.sleep(1000);
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testClient() {

		Assert.assertNotNull(client1.state);
		Assert.assertNotNull(client2.state);

	}

	@Test
	public void testBot() {

		Assert.assertTrue(bot1.init);
		Assert.assertTrue(bot2.init);

	}

	@Test
	public void testActions() {
		State currentState;
		currentState = getState(client1);
		Assert.assertEquals(currentState, State.LOGIN);
		for (ActionContext action : client1.state.getActions()) {
			if (action instanceof TypePlayerNameAction) {
				if (((TypePlayerNameAction) action).getKey() == 'a') {
					action.perform();
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client1), State.LOGIN);
		for (ActionContext action : client1.state.getActions()) {
			if (action instanceof SubmitPlayerNameAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client1), State.CONFIGUREGAME);
		for (ActionContext action : client1.state.getActions()) {
			if (action instanceof CreateNewDeckAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client1), State.EDITDECK);
		for (ActionContext action : client1.state.getActions()) {
			if (action instanceof DiscardDeckEditAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client1), State.CONFIGUREGAME);
		for (ActionContext action : client1.state.getActions()) {
			if (action instanceof FinishConfiguringAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client1), State.READYTOSTARTSTATE);

		Assert.assertEquals(getState(client2), State.LOGIN);
		for (ActionContext action : client2.state.getActions()) {
			if (action instanceof TypePlayerNameAction) {
				if (((TypePlayerNameAction) action).getKey() == 'b') {
					action.perform();
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client2), State.LOGIN);
		for (ActionContext action : client2.state.getActions()) {
			if (action instanceof SubmitPlayerNameAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client2), State.CONFIGUREGAME);
		for (ActionContext action : client2.state.getActions()) {
			if (action instanceof CreateNewDeckAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client2), State.EDITDECK);
		for (ActionContext action : client2.state.getActions()) {
			if (action instanceof DiscardDeckEditAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client2), State.CONFIGUREGAME);
		for (ActionContext action : client2.state.getActions()) {
			if (action instanceof FinishConfiguringAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(getState(client2), State.STOP);

		Assert.assertEquals(getState(client1), State.PLAYCARDSTATE);
		for (ActionContext action : client1.state.getActions()) {
			if (action instanceof PlayCardAction) {
				action.perform();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(getState(client1) == State.ADJUSTCARDHANDSTATE
				|| getState(client1) == State.STOP);
		Assert.assertTrue(getState(client2) == State.PLAYCARDSTATE
				|| getState(client2) == State.STOP);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private State getState(TestClient client) {
		return VisibleModelFields.PLAYERSTATE_STATE_SELF.getValue(client.state
				.getModel().getGenericTransfer());
	}
}
