package de.nordakademie.nakjava.gamelogic.gui;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class GUITest {

	private ActionContextDelegator delegator;
	private ContextHolder holder;

	@Before
	public void setUp() {
		delegator = ActionContextDelegator.getInstance();
		holder = new ContextHolder();
	}

	@Test
	public void testDelegator() {
		Assert.assertNotNull(delegator);

		List<ActionContext> contexts = new LinkedList<>();
		ActionContext playCardaction = new PlayCardAction("test", 1);
		contexts.add(playCardaction);
		delegator.delegateActionContexts(contexts, 1, true);

		delegator.registerActionContextHolder(holder);
		Assert.assertEquals(holder.batch, 1);
		Assert.assertEquals(holder.actionContext, playCardaction);

		contexts.clear();
		ActionContext withdraw = new WithdrawCardAction("test", 2);
		contexts.add(withdraw);
		delegator.delegateActionContexts(contexts, 2, false);
		Assert.assertEquals(holder.batch, 1);
		Assert.assertSame(holder.actionContext, playCardaction);
		Assert.assertEquals(holder.noActionContextAvailable, true);
		Assert.assertEquals(holder.revokeBatch, 1);
		contexts.add(playCardaction);
		delegator.delegateActionContexts(contexts, 3, false);
		Assert.assertEquals(holder.batch, 3);
		Assert.assertEquals(holder.actionContext, playCardaction);
		Assert.assertEquals(holder.noActionContextAvailable, true);
		Assert.assertEquals(holder.revokeBatch, 2);

	}
}
