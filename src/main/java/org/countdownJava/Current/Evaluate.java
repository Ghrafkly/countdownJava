package org.countdownJava.Current;

import java.util.HashMap;
import java.util.Map;

public class Evaluate {
//	public static Map<Operations, Integer> intermidiarySolutions = new HashMap<>();
	private int[] solutions;

	public int[] evaluate(int[][] postfixArray) {
		int solutionsIndex = 0;
		for (int[] postfix : postfixArray) {
			boolean valid = true;
			int[] stack = new int[11];
			int stackIndex = 0;

			for (int token : postfix) {
				int result = 0;

				if (!(tokenIsOp(token))) {
					stack[stackIndex++] = token;
				} else {
					int b = stack[--stackIndex], a = stack[--stackIndex];

					// deal with commutative operators i.e. a + b = b + a
//					if ((token == -1 || token == -3) && a < b) {
//						int temp = a;
//						a = b;
//						b = temp;
//					}
//
//					Operations op = new Operations(a, b, token);
//
					switch (token) {
						case -1 -> result = a + b;
						case -3 -> result = a * b;

						case -2 -> {
							// If result is negative, equation is not valid
							if (a > b) result = a - b;
							else valid = false;
						}
						case -4 -> {
							// If result is not an integer, equation is not valid
							if (a % b == 0) result = a / b;
							else valid = false;
						}
					}
					// if result becomes invalid, break loop
					if (!valid) break;

					stack[stackIndex++] = result;

					if (result >= 101 && result <= 999) {
						solutions[solutionsIndex++] = result;
					}
				}
			}
		}

		return solutions;
	}

	public boolean tokenIsOp(int token) {
		return token < 0;
	}

	public int[] getSolutions() {
		return solutions;
	}
}
