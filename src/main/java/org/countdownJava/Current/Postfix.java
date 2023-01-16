package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Postfix {
	private final Map<Integer, Long> solutionsMap = new ConcurrentHashMap<>();
	private final AtomicInteger numPostfix = new AtomicInteger();

	public void execute(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
//		versionOneOne(mapCombinationsPermutations);
//		versionOneTwo(mapCombinationsPermutations);
//		versionTwo(mapCombinationsPermutations);
		versionThree(mapCombinationsPermutations);
	}

	public void versionOneOne(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();
			List<CompletableFuture<Void>> futures = new ArrayList<>();

			for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {
				futures.add(CompletableFuture.supplyAsync(() -> {
					PostfixGen postfixGen = new PostfixGen(permutation);
					int[][] postfix = postfixGen.generatePostfix();
					numPostfix.addAndGet(postfix.length);

					return postfix;
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
		}
	}

	public void versionOneTwo(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();
			List<CompletableFuture<Void>> futures = new ArrayList<>();

			for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {
				futures.add(CompletableFuture.runAsync(() -> {
					PostfixGen postfixGen = new PostfixGen(permutation);
					int[][] postfix = postfixGen.generatePostfix();
					numPostfix.addAndGet(postfix.length);

					for (int solution : evaluatePostfix(postfix, intermidiarySolutions)) {
						solutionsMap.merge(solution, 1L, Long::sum);
					}
				}));
			}

			for (CompletableFuture<Void> future : futures) {
				future.get();
			}

			intermidiarySolutions.clear();
		}
	}

	public void versionTwo(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {

			futures.add(CompletableFuture.runAsync(() -> {
				Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();

				for (List<Integer> permutation : mapCombinationsPermutations.get(combination)) {
					PostfixGen postfixGen = new PostfixGen(permutation);
					int[][] postfix = postfixGen.generatePostfix();
					numPostfix.addAndGet(postfix.length);

					for (int solution : evaluatePostfix(postfix, intermidiarySolutions)) {
						solutionsMap.merge(solution, 1L, Long::sum);
					}
				}
			}));
		}

		for (CompletableFuture<Void> future : futures) {
			future.get();
		}
	}

	public void versionThree(Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(12);
		Map<List<Integer>, Future<int[][]>> futures = new HashMap<>();
		List<Future<int[]>> solutions = new ArrayList<>();

		int count = 0;
		for (Map.Entry<List<Integer>, List<List<Integer>>> combination : mapCombinationsPermutations.entrySet()) {
			for (List<Integer> permutation : combination.getValue()) {
				PostfixGen postfixGen = new PostfixGen(permutation);
				futures.put(permutation, executor.submit(() -> postfixGen.generatePostfix()));
			}

			Map<Operations, Integer> intermidiarySolutions = new ConcurrentHashMap<>();
			for (Map.Entry<List<Integer>, Future<int[][]>> future : futures.entrySet()) {
				solutions.add(executor.submit(() -> evaluatePostfix(future.getValue().get(), intermidiarySolutions)));
			}

			// add solutions to solutionsMap
			for (Future<int[]> solution : solutions) {
				try {
					for (int sol : solution.get()) {
						solutionsMap.merge(sol, 1L, Long::sum);
					}
				} catch (ExecutionException e) {
					System.out.println(count);
				}
			}

//
//			// clear solutions to save memory
			futures.clear();
			solutions.clear();
			intermidiarySolutions.clear();

			System.out.printf("Finished %d/%d combinations\r", count++, mapCombinationsPermutations.size());
		}


		// shutdown executors
		executor.shutdownNow();
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

	public int getNumPostfix() {
		return numPostfix.get();
	}

}
