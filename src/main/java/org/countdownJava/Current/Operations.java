package org.countdownJava.Current;

public record Operations(int a, int b, int op) {
	@Override
	public int a() {
		return a;
	}

	@Override
	public int b() {
		return b;
	}

	@Override
	public int op() {
		return op;
	}
}
