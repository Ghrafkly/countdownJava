package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Postfix {
	private Map<Integer, Long> solutionsMap = new HashMap<>();

	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(12);

		Map<List<Integer>, Future<int[]>> futures = new HashMap<>();

		for (Map.Entry<List<Integer>, List<List<Integer>>> combination : mapCombinationsPermutations.entrySet()) {
			futures.put(combination.getKey(), executor.submit(() -> evaluatePostfix(combination)));
		}

		executor.shutdownNow();

		// add solutions to map
		for (Map.Entry<List<Integer>, Future<int[]>> future : futures.entrySet()) {
			int[] solutions = future.getValue().get();
			for (int solution : solutions) {
				if (solutionsMap.containsKey(solution)) {
					solutionsMap.put(solution, solutionsMap.get(solution) + 1);
				} else {
					solutionsMap.put(solution, 1L);
				}
			}
		}

	}

	public int[] evaluatePostfix(Map.Entry<List<Integer>, List<List<Integer>>> combination) {
		Map<Operations, Integer> intermediarySolutions = new HashMap<>();
		Map<List<Integer>, int[][]> mapPermutationsPostfix = new HashMap<>();
		int[] solutions = new int[0];

		for (List<Integer> permutation : combination.getValue()) {
			PostfixGen postfixGen = new PostfixGen(permutation);
			mapPermutationsPostfix.put(permutation, postfixGen.generatePostfix());
		}

		for (int[][] postfix : mapPermutationsPostfix.values()) {
			solutions = IntStream.concat(Arrays.stream(solutions), Arrays.stream(evaluatePostfix(postfix, intermediarySolutions))).toArray();
		}

		mapPermutationsPostfix.clear();

		return solutions;
	}

	public int[] evaluatePostfix(int[][] postfixArray, Map<Operations, Integer> intermidiarySolutions) {
		int[] solutions = new int[150000];
		int solutionsIndex = 0;

		for (int[] postfix : postfixArray) {
			boolean valid = true;
			int[] stack = new int[11];
			int stackIndex = 0;

			for (int token : postfix) {
				int result = 0;
				boolean unique = true;

				if (!(tokenIsOp(token))) {
					stack[stackIndex++] = token;
				} else {
					int b = stack[--stackIndex], a = stack[--stackIndex];

					// deal with commutative operators i.e. a + b = b + a
					if ((token == -1 || token == -3) && a < b) {
						int temp = a;
						a = b;
						b = temp;
					}

					Operations op = new Operations(a, b, token);

					if (intermidiarySolutions.containsKey(op)) {
						result = intermidiarySolutions.get(op);
						unique = false;
					} else {
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
					}

					stack[stackIndex++] = result;
					intermidiarySolutions.put(op, result);

					// Add solutions if between 101 and 999 (inclusive)
					if (unique && result >= 101 && result <= 999) {
						solutions[solutionsIndex++] = result;
					}
				}
			}
		}

		// removes nulls from solutions
		return Arrays.stream(solutions).filter(i -> i != 0).toArray();
	}

	public boolean tokenIsOp(int token) {
		return token < 0;
	}

	public Map<Integer, Long> getSolutionsMap() {
		return solutionsMap;
	}

}
