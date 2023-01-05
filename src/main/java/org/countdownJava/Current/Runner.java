package org.countdownJava.Current;

import java.util.*;

public class Runner {
	public static Map<Integer, Long> solutions = new HashMap<>();
	public static int invalid = 0;

	private final Integer[] operators = {-1, -2, -3, -4};
	//	private final Integer[] numbers = {10, 25, 75, 100, 9, 8};
	private final Integer[] numbers = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};

	private Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();
	private final Map<List<Integer>, Integer[][]> mapPermutationsPostfix = new HashMap<>();

	private List<List<Integer>> setCombinations, setPermutations;
	private int numCombinations, numPermutations;
	private long numPostfix;

	private void combinations() {
		Combinations combinations = new Combinations();
		ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(numbers));

		setCombinations = combinations.generate(nums, numbers.length, 6);
		numCombinations = setCombinations.size();
	}

	private void permutations() {
		MyExecutor executor = new MyExecutor();
		executor.permutations(setCombinations);

		mapCombinationsPermutations = executor.getMapCombinationsPermutations();

		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			numPermutations += mapCombinationsPermutations.get(combination).size();
		}
	}

	private void startUp() {
		combinations();

		long startTime = System.nanoTime();
		permutations();
		long endTime = System.nanoTime();
		System.out.printf("Combinations: %d | Permutations: %d | Time: %d ms%n", numCombinations, numPermutations, (endTime - startTime) / 1000000);
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.startUp();
	}
}
