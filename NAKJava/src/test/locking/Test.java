package test.locking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

	private boolean test1 = false;
	private boolean test2 = false;

	public Test() {

		ExecutorService threadPool = Executors.newFixedThreadPool(2000);

		for (int i = 0; i < 1000; i++) {
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					test1();

				}
			});

			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					test2();

				}
			});

		}

		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void test1() {
		test1 = true;
		for (int i = 0; i < 10000; i++) {
			int j = i / 2;
			j++;
		}
		if (test2) {
			System.out.println("Überschneidung");
		}
		test1 = false;
	}

	private synchronized void test2() {
		test2 = true;
		for (int i = 0; i < 1000; i++) {
			int j = i / 2;
			j++;
		}
		if (test1) {
			System.out.println("Überschneidung");
		}
		test2 = false;
	}

	public static void main(String[] args) {
		new Test();
	}
}
