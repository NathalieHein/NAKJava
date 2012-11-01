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
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.VisibleModelUpdater;

public abstract class ActionAbstractImpl extends UnicastRemoteObject implements
		ServerAction {

	protected ActionAbstractImpl(long sessionNr) throws RemoteException {
		super();
		this.sessionNr = sessionNr;
	}

	// TODO implement shutDown method after use? If not needed than simply
	// Executor-Interface?
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private int threadCount = 2;

	private Lock lock = new ReentrantLock();
	private Condition waitCondition;

	private long sessionNr;

	@Override
	public void perform() throws RemoteException {
		final Session session = ActionBroker.getInstance().verify(this);
		if (session != null) {
			performImpl(session);
			final long batchNr = Batch.increaseAndGetBatchNr();
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					ActionRuleset.update(session, batchNr);
					finishThread();
				}
			});
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					VisibleModelUpdater.update(session);
					finishThread();
				}
			});

			// Needs to be done for changing the thread context back
			// to the thread that holds the lock in the ActionBroker
			// TODO this is easier if using thread.join()
			waitCondition = lock.newCondition();
			lock.lock();
			try {
				waitCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();

			ActionBroker.getInstance().commit(this);

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

	protected abstract void performImpl(Session model);

	public long getSessionNr() {
		return sessionNr;
	}

	public void setSessionNr(long sessionNr) {
		this.sessionNr = sessionNr;
	}

}
