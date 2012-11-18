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

	// Executor-Interface?
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	private int threadCount = 2;

	private final Lock lock = new ReentrantLock();
	private final Condition waitCondition = lock.newCondition();

	private long sessionId;

	@Override
	public void perform() throws RemoteException {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				boolean verified = false;
				// synchronized (ActionBroker.getInstance()) {
				verified = ActionBroker.getInstance().verify(
						ActionAbstractImpl.this);
				// }
				if (verified) {
					System.out.println(System.currentTimeMillis()
							+ "Action verified");
					Session session = Sessions.getInstance().getSession(
							sessionId);
					performImpl(session);
					System.out
							.println(System.currentTimeMillis() + "performed");
					// Needs to be done for changing the thread context back
					// to the thread that holds the lock in the ActionBroker
					// TODO this is easier if using thread.join()
					// TODO this needs to be put here => otherwise deadlock
					lock.lock();
					System.out.println(System.currentTimeMillis() + "locked");
					threadPool.execute(new Runnable() {

						@Override
						public void run() {
							System.out.println(System.currentTimeMillis()
									+ "ARupdated");
							ActionRuleset.update(sessionId);
							finishThread();
						}
					});
					threadPool.execute(new Runnable() {
						@Override
						public void run() {
							System.out.println(System.currentTimeMillis()
									+ "VMupdated");
							VisibleModelUpdater.update(sessionId);
							finishThread();
						}
					});

					try {
						waitCondition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					System.out.println(System.currentTimeMillis() + "commit");
					ActionBroker.getInstance().commit(ActionAbstractImpl.this);

				}
			}
		});

	}

	private synchronized void finishThread() {
		threadCount--;
		System.out.println(System.currentTimeMillis() + "finishThread");
		if (threadCount == 0) {
			lock.lock();
			try {
				waitCondition.signal();
			} finally {
				lock.unlock();
			}
		}
	}

	protected abstract void performImpl(Session session);

	public boolean isServerInternal() {
		return false;
	}

	public long getSessionNr() {
		return sessionId;
	}

	public void setSessionNr(long sessionNr) {
		this.sessionId = sessionNr;
	}

}
