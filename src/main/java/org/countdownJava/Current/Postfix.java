package org.countdownJava.Current;

import java.util.Arrays;

public class Postfix {
	private final char[] operators, permutations;
	private final char[][] postfixArray;
	private int currentIndex = 0, listIndex = 0, postfixIndex = 0;
	private Decoder decoder = new Decoder();
	public Postfix(char[] operators, char[] permutations) {
		// 43008 - 1344 = 41664
		this.postfixArray = new char[41664][11];
		this.operators = operators;
		this.permutations = permutations;
	}

	public char[][] generate(char[] current, int opsNeeded) {
		if (opsNeeded == 0 && listIndex == permutations.length) {
			char[] copy = Arrays.copyOf(current, current.length);

			int opCheck = 0;
			for (char token : copy) {
				if (token == 'p') {
					opCheck++;
				}
				if (token == 'r') {
					opCheck++;
				}
			}

			if (opCheck == 5) {
				Runner.invalid++;
			}

			if (opCheck < 5) {
				postfixArray[postfixIndex++] = copy;
			}

		}

		if (opsNeeded > 0) {
			for (char operator : operators) {
				current[currentIndex++] = operator;
				generate(current, opsNeeded - 1);
				current[--currentIndex] = '\0';
			}
		}

		if (listIndex < permutations.length) {
			char hold = permutations[listIndex++];
			current[currentIndex++] = hold;
			generate(current, opsNeeded + 1);
			current[--currentIndex] = '\0';
			permutations[--listIndex] = hold;
		}

		return postfixArray;
	}
}
