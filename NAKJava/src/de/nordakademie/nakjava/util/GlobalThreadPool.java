package de.nordakademie.nakjava.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GlobalThreadPool {

	private static ExecutorService threadPool;

	public static void init(int threads) {
		threadPool = Executors.newFixedThreadPool(threads);
	}

	public static void perform(Runnable runnable) {
		threadPool.execute(runnable);
	}
}
