package org.countdownJava;

import java.util.*;

public class Evaluate {
	private List<List<String>> postfixList;
	public static int invalidEquations = 0;
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
							if (a > b) {
								stack.push(a - b);
							} else {
								valid = false;
							}
						}
						case "/" -> {
							// If result is not an integer, equation is not valid
							if (a % b == 0) {
								stack.push(a / b);
							} else {
								valid = false;
							}
						}
						default -> throw new IllegalStateException("Unexpected value: " + token);
					}
				}
			}

			// If equation is valid, add result to list
			if (valid) {
				resultList.add(stack.pop());
				validEquations++;
			} else {
				invalidEquations++;
			}
		}

		// Add solutions to map
		for (int result : resultList) {
			if (solutions.containsKey(result)) {
				solutions.put(result, solutions.get(result) + 1);
			} else {
				solutions.put(result, 1);
			}
		}
	}
}
