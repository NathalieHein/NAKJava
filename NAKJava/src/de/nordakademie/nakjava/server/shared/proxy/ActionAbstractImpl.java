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
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.VisibleModelUpdater;

public abstract class ActionAbstractImpl extends UnicastRemoteObject implements
		ServerAction {

	// TODO THIS is not possible because RemoteObject needs constructor without
	// parameters, otherwise RemoteException
	protected ActionAbstractImpl(long sessionId) throws RemoteException {
		super();
		this.sessionId = sessionId;
	}

	// TODO implement shutDown method after use? If not needed than simply
	// Executor-Interface?
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private int threadCount = 2;

	private Lock lock = new ReentrantLock();
	private Condition waitCondition;

	private long sessionId;

	@Override
	public void perform() throws RemoteException {
		if (ActionBroker.getInstance().verify(this)) {
			Session session = Sessions.getInstance().getSession(sessionId);
			performImpl(session);
			session = Sessions.getInstance().getSession(sessionId);
			session.getBatch().increaseBatchNr();
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					ActionRuleset.update(sessionId);
					finishThread();
				}
			});
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					VisibleModelUpdater.update(sessionId);
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
		return sessionId;
	}

	public void setSessionNr(long sessionNr) {
		this.sessionId = sessionNr;
	}

}
