package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Postfix {
	private final Map<Integer, Long> solutionsMap = new ConcurrentHashMap<>();
	private final AtomicLong numPostfix = new AtomicLong();
	private final AtomicInteger comb = new AtomicInteger();

	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			futures.add(CompletableFuture.runAsync(() -> {
				Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();

				for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {
					PostfixGen postfixGen = new PostfixGen(permutation);
					int[][] postfix = postfixGen.generatePostfix();
					numPostfix.addAndGet(postfix.length);

//					for (Integer solution : evaluatePostfix(postfix, intermidiarySolutions)) {
//						solutionsMap.merge(solution, 1L, Long::sum);
//					}
				}
				comb.incrementAndGet();
			}));
		}

		int hold = 0;
		while (comb.get() < mapCombinationsPermutations.size()) {
			if (hold != comb.get()) {
				if (hold == mapCombinationsPermutations.size()) {
					System.out.printf("Combination: %d / %d%n", comb.get(), mapCombinationsPermutations.size());
				} else {
					System.out.printf("Combination: %d / %d\r", comb.get(), mapCombinationsPermutations.size());
				}
				hold = comb.get();
			}
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

						// Intermediary solutions that would result in an invalid solution
						if (result == -1) break;

						stack[stackIndex++] = result;
						unique = false;
					} else {
						// -1 = +, -2 = -, -3 = *, -4 = /
						switch (token) {
							case -1 -> result = a + b;

							// If result is negative, equation is not valid
							case -2 -> {
								if (a > b) {
									result = a - b;
									// a - b = b
									if (result == b) unique = false;
								}
								else valid = false;
							}

							case -3 -> {
								// deal with multiplication by 1
								if (a == 1 || b == 1) unique = false;
								result = a * b;
							}

							// If result is not an integer, equation is not valid
							case -4 -> {
								if (a % b == 0) {
									// deal with division by 1
									if (b == 1) unique = false;
									result = a / b;
									// a / b = b
									if (result == b) unique = false;
								}
								else valid = false;
							}
						}

						// add -1 to map to indicate that this a b token is invalid
						// if result becomes invalid, break loop
						if (valid) {
							intermidiarySolutions.merge(op, result, (oldValue, newValue) -> newValue);
						} else {
							intermidiarySolutions.merge(op, -1, (oldValue, newValue) -> -1);
							break;
						}

						stack[stackIndex++] = result;
					}

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
