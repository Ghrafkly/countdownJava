package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Runner {

//	private final Integer[] numbers = {10, 25, 75, 100, 9, 8};
	private final Integer[] numbers = {10, 25, 75, 100, 9, 8, 7};
//	private final Integer[] numbers = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};
	private int R = 6;

	private Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();
	private final Map<List<Integer>, int[][]> mapPermutationsPostfix = new HashMap<>();
	private Map<Integer, Long> solutions = new HashMap<>();

	private List<List<Integer>> setCombinations;
	private int numCombinations, numPermutations;
	private long numPostfix, numSolutions;

	private void combinations() {
		Combinations combinations = new Combinations();
		ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(numbers));
		setCombinations = combinations.generate(nums, numbers.length, R);
	}

	private void permutations() throws ExecutionException, InterruptedException {
		Permutations executor = new Permutations();
		executor.execute(setCombinations);
		mapCombinationsPermutations = executor.getMapCombinationsPermutations();
	}

	private void postfix() throws ExecutionException, InterruptedException {
		Postfix executor = new Postfix();
		executor.execute(mapCombinationsPermutations);
		solutions = executor.getSolutionsMap();

		numPostfix = executor.getNumPostfix();
	}

	private void startUp() throws ExecutionException, InterruptedException {
		long startTime, endTime;

		startTime = System.currentTimeMillis();
		combinations();
		permutations();
		postfix();
		endTime = System.currentTimeMillis();

		counts();
//		solutions();

		System.out.printf("Combinations: %d | Permutations: %d | Postfix: %d | Solutions: %d | Time: %d ms%n",
				numCombinations, numPermutations, numPostfix, numSolutions, endTime - startTime);

		WriteToFile writeToFile = new WriteToFile();
		writeToFile.write(solutions);
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Runner runner = new Runner();
		runner.startUp();
	}

	private void counts() {
		numCombinations = setCombinations.size();

		for (List<Integer> combination : mapCombinationsPermutations.keySet()) {
			numPermutations += mapCombinationsPermutations.get(combination).size();
		}

		for (Map.Entry<Integer, Long> entry : solutions.entrySet()) {
			numSolutions += entry.getValue();
		}
	}

	private void solutions() {
		for (int i = 0; i < 1000; i++) {
			if (solutions.containsKey(i)) {
				System.out.printf("%d: %d%n", i, solutions.get(i));
			}
		}
	}
}
