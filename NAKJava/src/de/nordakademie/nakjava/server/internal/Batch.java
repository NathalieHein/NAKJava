package de.nordakademie.nakjava.server.internal;

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
	private long batch = 0;

	public void increaseBatchNr() {
		batch++;
	}

	public long getCurrentBatchNr() {
		return batch;
	}

}
