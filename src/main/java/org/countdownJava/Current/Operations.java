package org.countdownJava.Current;

public record Operations(int a, int b, byte op) {
	@Override
	public int a() {
		return a;
	}

	@Override
	public int b() {
		return b;
	}

	@Override
	public byte op() {
		return op;
	}
}
