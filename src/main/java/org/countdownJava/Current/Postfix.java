package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Postfix {
	private final Map<Integer, Long> solutionsMap = new ConcurrentHashMap<>();
	private final Map<List<Integer>, int[]> mapPermutationsSolutions = new ConcurrentHashMap<>();

	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		long startTime, endTime;
		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			startTime = System.currentTimeMillis();
			Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();
			List<CompletableFuture<Void>> futures = new ArrayList<>();

			for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {

				futures.add(CompletableFuture.supplyAsync(() -> {
					PostfixGen postfixGen = new PostfixGen(permutation);
					return postfixGen.generatePostfix();
				}).thenAcceptAsync((postfix) -> {
					int[] solutions = evaluatePostfix(postfix, intermidiarySolutions);
					for (int solution : solutions) {
						solutionsMap.merge(solution, 1L, Long::sum);
					}
				}));

			}

			for (CompletableFuture<Void> future : futures) {
				future.get();
			}

			intermidiarySolutions.clear();

			endTime = System.currentTimeMillis();
			System.out.printf("Combination: %d ms%n", endTime - startTime);
		}
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
