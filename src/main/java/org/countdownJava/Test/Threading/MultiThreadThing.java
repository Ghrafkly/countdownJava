package org.countdownJava.Test.Threading;

public class MultiThreadThing extends Thread {
	private int threadNumber;
	public MultiThreadThing(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public void count() {
		for (int i = 0; i < 5; i++) {
			System.out.printf("Thread: %d = %d%n", i, threadNumber);


			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {}

		}
	}

	@Override
	public void run() {
		count();
	}
}
