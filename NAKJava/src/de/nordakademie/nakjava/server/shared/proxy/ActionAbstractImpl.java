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

/**
 * Abstract Implementation of Server-Action that does processing common to every
 * ServerAction
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class ActionAbstractImpl extends UnicastRemoteObject implements
		ServerAction {
	/**
	 * 
	 * @param sessionId
	 *            : used to identify the session this action belongs to
	 * @throws RemoteException
	 */
	protected ActionAbstractImpl(long sessionId) throws RemoteException {
		super();
		this.sessionId = sessionId;
	}

	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	private int threadCount = 2;

	private final Lock lock = new ReentrantLock();
	private final Condition waitCondition = lock.newCondition();

	private long sessionId;

	/**
	 * processing of a client's request: needs verification of validity of
	 * ServerAction + locking of corresponding session for processing; updating
	 * the model of the session(performImpl()); creating new Actions and
	 * extracting the visible-status of the model(is done with multithreading
	 * because only read-access to model); commit(update to be send to
	 * client+releasing the session-lock)
	 */
	@Override
	public void perform() throws RemoteException {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				boolean verified = false;
				verified = ActionBroker.getInstance().verify(
						ActionAbstractImpl.this);
				if (verified) {
					Session session = Sessions.getInstance().getSession(
							sessionId);
					performImpl(session);
					lock.lock();
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

					try {
						waitCondition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					ActionBroker.getInstance().commit(ActionAbstractImpl.this);

				}
			}
		});

	}

	// used for changing the threadcontext back to the thread that acquired the
	// lock on the session
	private synchronized void finishThread() {
		threadCount--;
		if (threadCount == 0) {
			lock.lock();
			try {
				waitCondition.signal();
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 
	 * @param session
	 *            : Session-object on which this ServerAction operates
	 */
	protected abstract void performImpl(Session session);

	/**
	 * 
	 * @return: returns true, if serverInternal-action that is verified whithout
	 *          knowing the session
	 */
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
