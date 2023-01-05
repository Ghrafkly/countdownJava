package org.countdownJava.Test.Executor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleExecutor {

	private void startUp() {
		ArrayList<MyThread> threads = new ArrayList<>();
		// Create a thread pool with two threads
		ExecutorService executor = Executors.newFixedThreadPool(5);

		// Create a CountDownLatch object
		CountDownLatch latch = new CountDownLatch(5);

		// Create and start 5 threads
		for (int i = 0; i < 100; i++) {
			threads.add(new MyThread(latch, "Thread " + i));
			executor.execute(threads.get(i));
		}

		// Wait for threads to finish
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Shut down the executor
		executor.shutdown();
		System.out.println("All threads finished");
	}
	public static void main(String[] args) {
		SimpleExecutor executor = new SimpleExecutor();
		executor.startUp();
	}
}
