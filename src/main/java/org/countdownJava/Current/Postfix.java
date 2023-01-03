package org.countdownJava.Current;

import java.util.Arrays;

public class Postfix {
	private final byte[] operators, permutations;
	private final byte[][] postfixArray;
	private int currentIndex = 0, listIndex = 0, postfixIndex = 0;

	public Postfix(byte[] permutations, byte[] operators) {
		// 43008 without remove
		// 41664 removal of - and/or /
		// 41580 removal of only * or only +
		this.postfixArray = new byte[43008][11];
		this.operators = operators;
		this.permutations = permutations;
	}

	public byte[][] generate(byte[] current, int opsNeeded) {
		if (opsNeeded == 0 && listIndex == permutations.length) {
			byte[] copy = Arrays.copyOf(current, current.length);
			postfixArray[postfixIndex++] = copy;

//			if (remove(copy)) {
//				postfixArray[postfixIndex++] = copy;
//			} else {
//				Runner.invalid++;
//			}
		}

		if (opsNeeded > 0) {
			for (byte operator : operators) {
				current[currentIndex++] = operator;
				generate(current, opsNeeded - 1);
				current[--currentIndex] = 0;
			}
		}

		if (listIndex < permutations.length) {
			byte hold = permutations[listIndex++];
			current[currentIndex++] = hold;
			generate(current, opsNeeded + 1);
			current[--currentIndex] = 0;
			permutations[--listIndex] = hold;
		}

		return postfixArray;

	}
}
