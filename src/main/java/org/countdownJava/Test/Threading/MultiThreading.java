package org.countdownJava.Test.Threading;

public class MultiThreading {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 5; i++) {;
			MultiThreadThing thread = new MultiThreadThing(i);
			thread.start();
		}

	}
}
