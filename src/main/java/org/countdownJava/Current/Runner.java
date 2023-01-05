package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Runner {
	public static Map<Integer, Long> solutions = new HashMap<>();
	public static int invalid = 0;

	private final Integer[] operators = {-1, -2, -3, -4};
//		private final Integer[] numbers = {10, 25, 75};
		private final Integer[] numbers = {1, 2, 3};
//	private final Integer[] numbers = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};

	private Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();
	private Map<List<Integer>, int[][]> mapPermutationsPostfix = new HashMap<>();

	private List<List<Integer>> setCombinations, setPermutations;
	private int numCombinations, numPermutations;
	private long numPostfix;

	private void combinations() {
		Combinations combinations = new Combinations();
		ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(numbers));
		setCombinations = combinations.generate(nums, numbers.length, 3);
	}

	private void permutations() throws ExecutionException, InterruptedException {
		Permutations executor = new Permutations();
		executor.permutations(setCombinations); // List<List<Integer>> setCombinations
		mapCombinationsPermutations = executor.getMapCombinationsPermutations();
	}

	private void postfix() throws ExecutionException, InterruptedException {
		Postfix executor = new Postfix();
		executor.postfix(mapCombinationsPermutations);
		mapPermutationsPostfix = executor.getMapPermutationsPostfix();
	}

	private void startUp() throws ExecutionException, InterruptedException {
		long startTime, endTime;

		startTime = System.currentTimeMillis();
		combinations();
		permutations();
		postfix();
		endTime = System.currentTimeMillis();

		count();

		System.out.printf("Combinations: %d | Permutations: %d | Postfix: %d | Time: %d ms%n",
				numCombinations, numPermutations, numPostfix, endTime - startTime);
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Runner runner = new Runner();
		runner.startUp();
	}

	private void count() {
		numCombinations = setCombinations.size();

		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			numPermutations += mapCombinationsPermutations.get(combination).size();
		}

		for (List<Integer> permutation : mapPermutationsPostfix.keySet()) {
			numPostfix += mapPermutationsPostfix.get(permutation).length;
		}
	}
}
