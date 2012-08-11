package de.nordakademie.nakjava.server.internal;

import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;

public class Batch {
	private static long batch = 0;

	/**
	 * Does not need to be synchronized because it is called from a single
	 * threaded context in {@link ActionAbstractImpl}
	 * 
	 * 
	 * TODO ausrechnen Überlauf
	 */

	public static void increaseBatchNr() {
		batch++;
	}

	public static long getCurrentBatchNr() {
		return batch;
	}
}
