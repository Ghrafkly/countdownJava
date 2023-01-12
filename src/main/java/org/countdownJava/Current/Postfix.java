package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Postfix {
	private final Map<Integer, Long> solutionsMap = new HashMap<>();
	private final Map<List<Integer>, int[]> mapPermutationsSolutions = new ConcurrentHashMap<>();

	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();
			for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {
				// supply async to generate postfix, then apply async to evaluate postfix
				CompletableFuture<int[][]> future = CompletableFuture.supplyAsync(() -> {
					PostfixGen postfixGen = new PostfixGen(permutation);
					return postfixGen.generatePostfix();
				}).thenApplyAsync((postfix) -> {
					evaluatePostfix(postfix, intermidiarySolutions);
					return postfix;
				});
			}
		}

		System.out.println(solutionsMap);
	}

//	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
//		ExecutorService executorPostfix = Executors.newFixedThreadPool(6);
//		ExecutorService executorEvaluate = Executors.newFixedThreadPool(6);
//		Map<List<Integer>, Future<int[][]>> futures = new HashMap<>();
//		List<Future<int[]>> solutions = new ArrayList<>();
//
//		for (Map.Entry<List<Integer>, List<List<Integer>>> combination : mapCombinationsPermutations.entrySet()) {
//			for (List<Integer> permutation : combination.getValue()) {
//				PostfixGen postfixGen = new PostfixGen(permutation);
//				futures.put(permutation, executorPostfix.submit(() -> postfixGen.generatePostfix()));
//			}
//
//			Map<Operations, Integer> intermidiarySolutions = new HashMap<>();
//			for (List<Integer> permutation : combination.getValue()) {
//				solutions.add(executorEvaluate.submit(() -> evaluatePostfix(futures.get(permutation).get(), intermidiarySolutions)));
//			}
//
//			// add solutions to solutionsMap
//			for (Future<int[]> solution : solutions) {
//				System.out.println(solution);
//				for (int result : solution.get()) {
//					solutionsMap.put(result, solutionsMap.getOrDefault(result, 0L) + 1);
//				}
//			}
//
//			// clear futures and solutions to save memory
//			futures.clear();
//			solutions.clear();
//		}
//
//		// shutdown executors
//		executorPostfix.shutdown();
//		executorEvaluate.shutdown();
//	}

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
