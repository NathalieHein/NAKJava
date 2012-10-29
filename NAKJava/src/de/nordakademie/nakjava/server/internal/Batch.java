package de.nordakademie.nakjava.server.internal;

import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

/**
 * Batch is used to identify {@link PlayerModel} and associated
 * {@link ServerAction}
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class Batch {
	private static long batch = 0;

	/**
	 * Does not need to be synchronized because it is called from a single
	 * threaded context in {@link ActionAbstractImpl}
	 * 
	 * --> this is not true anymore TODO ausrechnen Überlauf
	 */

	/*
	 * public static void increaseBatchNr() { batch++; }
	 * 
	 * public static long getCurrentBatchNr() { return batch; }
	 */

	public synchronized static long increaseAndGetBatchNr() {
		batch++;
		return batch;
	}
}
