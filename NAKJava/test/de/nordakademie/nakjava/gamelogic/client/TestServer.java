package de.nordakademie.nakjava.gamelogic.client;

import java.rmi.RemoteException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.nordakademie.nakjava.server.shared.proxy.CheckInImpl;

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
}
