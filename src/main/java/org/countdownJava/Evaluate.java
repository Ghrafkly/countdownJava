package org.countdownJava;

import java.util.Arrays;

public class Evaluate {
	private final String[][] postfixArray;
	public static int invalidEquations = 0, validEquations = 0, invalidSolutions = 0, validSolutions = 0;

	public Evaluate(String[][] postfixArray) {
		this.postfixArray = postfixArray;
	}

	public void evaluate() {
		int[] resultList = new int[postfixArray.length];

		for (String[] postfix : postfixArray) {
			boolean valid = true;
			int[] stack = new int[11];
			int stackIndex = 0;

			for (String token : postfix) {
				// Checks if the equation remains valid
				if (!valid) break;

				// Evaluates the token
				if (token.matches("[0-9]+")) {
					stack[stackIndex++] = Integer.parseInt(token);
				} else {
					int b = stack[--stackIndex], a = stack[--stackIndex];
					switch (token) {
						case "+" -> stack[stackIndex++] = a + b;
						case "*" -> stack[stackIndex++] = a * b;

						case "-" -> {
							// If result is negative, equation is not valid
							if (a > b) stack[stackIndex++] = a - b;
							else valid = false;
						}
						case "/" -> {
							// If result is not an integer, equation is not valid
							if (a % b == 0) stack[stackIndex++] = a / b;
							else valid = false;
						}
						default -> throw new IllegalStateException("Unexpected value: " + token);
					}
				}
			}

			/*
			 If equation is valid, add result to list
			 Valid equations are the following
			 1. The result is an integer
			 2. The result is between 101 and 999
			*/

			if (!valid) {
				invalidEquations++;
			} else {
				int result = stack[--stackIndex];
				validEquations++;

				if (result >= 101 && result <= 999) {
					resultList[validSolutions++] = result;
				} else {
					invalidSolutions++;
				}
			}
		}

		// Prints the results
		for (int result : resultList) {
			if (result != 0) {
				Runner.solutions.put(result, Runner.solutions.getOrDefault(result, 0) + 1);
			}
		}
	}
}
