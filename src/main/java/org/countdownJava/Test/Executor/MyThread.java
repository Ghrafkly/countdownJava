package org.countdownJava.Test.Executor;

import java.util.concurrent.CountDownLatch;

public class MyThread implements Runnable {
	// Class data members
	private final String name;
	private final CountDownLatch latch;
	private String test;

	// Constructor
	public MyThread(CountDownLatch latch, String name) {

		// this keyword refers to current instance itself
		this.name = name;
		this.latch = latch;

		new Thread(this);
	}

	public void assignTest(String test) {
		this.test = test;
	}

	public String getName() {
		return name;
	}

	// Method
	// Called automatically when thread is started
	public void run() {
		System.out.println(name + " is running");
		latch.countDown();
	}
}
