package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.nordakademie.nakjava.server.internal.ActionBroker;
import de.nordakademie.nakjava.server.internal.ActionRuleset;
import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.internal.VisibleModelUpdater;

public abstract class ActionAbstractImpl extends UnicastRemoteObject implements
		Action {

	protected ActionAbstractImpl() throws RemoteException {
		super();
	}

	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private int threadCount = 2;

	@Override
	public void perform() throws RemoteException {
		if (ActionBroker.getInstance().verify(this)) {
			performImpl(Model.getInstance());
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					ActionRuleset.getInstance().update();
					finishThread();
				}
			});
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					VisibleModelUpdater.getInstance().update();
					finishThread();
				}
			});

		}

	}

	private synchronized void finishThread() {
		threadCount--;
		if (threadCount == 0) {
			ActionBroker.getInstance().commit();
		}
	}

	protected abstract void performImpl(Model mod);

}
