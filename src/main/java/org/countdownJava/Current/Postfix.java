package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Postfix {
	private final Map<Integer, Long> solutionsMap = new ConcurrentHashMap<>();
	private final AtomicLong numPostfix = new AtomicLong();

	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			futures.add(CompletableFuture.runAsync(() -> {
				Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();

				for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {
					PostfixGen postfixGen = new PostfixGen(permutation);
					int[][] postfix = postfixGen.generatePostfix();
					numPostfix.addAndGet(postfix.length);

					evaluatePostfix(postfix, intermidiarySolutions).forEach(solution -> solutionsMap.merge(solution, 1L, Long::sum));
				}
			}));
		}

		for (CompletableFuture<Void> future : futures) {
			future.get();
		}
	}

	public ArrayList<Integer> evaluatePostfix(int[][] postfixArray, Map<Operations, Integer> intermidiarySolutions) {
		ArrayList<Integer> solutionsList = new ArrayList<>();

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
							// If result is negative, equation is not valid
							case -2 -> {
								if (a > b) result = a - b;
								else valid = false;
							}
							// If result is not an integer, equation is not valid
							case -4 -> {
								if (a % b == 0) result = a / b;
								else valid = false;
							}
						}
						// if result becomes invalid, break loop
						if (!valid) break;
					}

					stack[stackIndex++] = result;
				    intermidiarySolutions.merge(op, result, (x, y) -> {
				        if (x.equals(y)) return x;
				        else throw new IllegalStateException("Duplicate key");
					});

					// Add solutions if between 101 and 999 (inclusive)
					if (unique && result >= 101 && result <= 999) solutionsList.add(result);
				}
			}
		}

		return solutionsList;
	}


	public boolean tokenIsOp(int token) {
		return token < 0;
	}

	public Map<Integer, Long> getSolutionsMap() {
		return solutionsMap;
	}

	public long getNumPostfix() {
		return numPostfix.get();
	}

}
