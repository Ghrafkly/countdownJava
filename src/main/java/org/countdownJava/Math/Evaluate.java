package org.countdownJava.Math;

import java.util.*;
import java.util.stream.IntStream;

public class Evaluate {
	private final List<List<String>> postfixList;
	public static int invalidEquations = 0;
	public static int invalidSolutions = 0;
	public static int validEquations = 0;
	public static Map<Integer, Integer> solutions = new HashMap<>();

	public Evaluate(List<List<String>> postfixList) {
		this.postfixList = postfixList;
	}

	public void evaluate() {
		List<Integer> resultList = new ArrayList<>();

		for (List<String> postfix : postfixList) {
			boolean valid = true;
			Stack<Integer> stack = new Stack<>();

			for (String token : postfix) {
				// Checks if the equation remains valid
				if (!valid) break;

				// Evaluates the token
				if (token.matches("[0-9]+")) {
					stack.push(Integer.parseInt(token));
				} else {
					int b = stack.pop(), a = stack.pop();
					switch (token) {
						case "+" -> stack.push(a + b);
						case "*" -> stack.push(a * b);

						case "-" -> {
							// If result is negative, equation is not valid
							if (a > b) stack.push(a - b);
							else valid = false;
						}
						case "/" -> {
							// If result is not an integer, equation is not valid
							if (a % b == 0) stack.push(a / b);
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

			if (valid) {
				int result = stack.pop();

				if (result > 100 && result < 1000) {
					resultList.add(result);
					validEquations++;
				} else {
					invalidSolutions++;
				}
			} else {
				invalidEquations++;
			}
		}

		// Create a map of values from 101-999 and the number of times they appear in the list
		IntStream.range(101, 1000).forEach(i -> solutions.put(i, 0));
		resultList.forEach(result -> solutions.put(result, solutions.get(result) + 1));
	}
}
