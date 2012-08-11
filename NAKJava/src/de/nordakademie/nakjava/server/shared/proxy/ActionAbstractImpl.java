package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.server.internal.ActionBroker;
import de.nordakademie.nakjava.server.internal.ActionRuleset;
import de.nordakademie.nakjava.server.internal.Batch;
import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.internal.VisibleModelUpdater;

public abstract class ActionAbstractImpl extends UnicastRemoteObject implements
		ServerAction {

	protected ActionAbstractImpl() throws RemoteException {
		super();
	}

	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private int threadCount = 2;

	private Lock lock = new ReentrantLock();
	private Condition waitCondition;

	@Override
	public void perform() throws RemoteException {
		if (ActionBroker.getInstance().verify(this)) {
			performImpl(Model.getInstance());
			Batch.increaseBatchNr();
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					ActionRuleset.getInstance().update(
							Batch.getCurrentBatchNr());
					finishThread();
				}
			});
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					VisibleModelUpdater.getInstance().update(
							Batch.getCurrentBatchNr());
					finishThread();
				}
			});

			// Needs to be done for changing the thread context back
			// to the thread that holds the lock in the ActionBroker
			waitCondition = lock.newCondition();
			lock.lock();
			try {
				waitCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();

			ActionBroker.getInstance().commit();

		}

	}

	private synchronized void finishThread() {
		threadCount--;
		if (threadCount == 0) {
			lock.lock();
			waitCondition.signal();
			lock.unlock();
		}
	}

	protected abstract void performImpl(Model model);

}
