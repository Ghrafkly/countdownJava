package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Runner {

//	private final Integer[] numbers = {10, 25, 75, 100, 9, 8};
//	private final Integer[] numbers = {10, 25, 75, 100, 9, 8, 7};
//	private final Integer[] numbers = {10, 25, 75, 100, 9, 8, 7, 6};
	private final Integer[] numbers = {1, 2, 10, 10, 25, 50, 75, 100};
//	private final Integer[] numbers = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};

	private Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();
	private Map<Integer, Long> solutions = new HashMap<>();

	private List<List<Integer>> setCombinations;
	private int numCombinations, numPermutations;
	private long numPostfix, numSolutions;

	private void combinations() {
		Combinations combinations = new Combinations();
		ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(numbers));
		setCombinations = combinations.generate(nums, numbers.length, 6);
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
		long startTime, combTime, permTime, postfixTime;

		startTime = System.nanoTime();

		combinations();
		combTime = System.nanoTime() - startTime;

		permutations();
		permTime = System.nanoTime() - combTime - startTime;
//
		postfix();
		postfixTime = System.nanoTime() - permTime - combTime - startTime;

		counts();
//		solutions();

		if (combTime > 1000000000) {
			System.out.println("Converted combTime to seconds");
			combTime = TimeUnit.SECONDS.convert(combTime, TimeUnit.NANOSECONDS);
		} else if (combTime > 1000000) {
			System.out.println("Converted combTime to milliseconds");
			combTime = TimeUnit.MILLISECONDS.convert(combTime, TimeUnit.NANOSECONDS);
		} else if (combTime > 1000) {
			System.out.println("Converted combTime to microseconds");
			combTime = TimeUnit.MICROSECONDS.convert(combTime, TimeUnit.NANOSECONDS);
		}

		if (permTime > 1000000000) {
			System.out.println("Converted permTime to seconds");
			permTime = TimeUnit.SECONDS.convert(permTime, TimeUnit.NANOSECONDS);
		} else if (permTime > 1000000) {
			System.out.println("Converted permTime to milliseconds");
			permTime = TimeUnit.MILLISECONDS.convert(permTime, TimeUnit.NANOSECONDS);
		} else if (permTime > 1000) {
			System.out.println("Converted permTime to microseconds");
			permTime = TimeUnit.MICROSECONDS.convert(permTime, TimeUnit.NANOSECONDS);
		}

		if (postfixTime > 1000000000) {
			System.out.println("Converted postfixTime to seconds");
			postfixTime = TimeUnit.SECONDS.convert(postfixTime, TimeUnit.NANOSECONDS);
		} else if (postfixTime > 1000000) {
			System.out.println("Converted postfixTime to milliseconds");
			postfixTime = TimeUnit.MILLISECONDS.convert(postfixTime, TimeUnit.NANOSECONDS);
		} else if (postfixTime > 1000) {
			System.out.println("Converted postfixTime to microseconds");
			postfixTime = TimeUnit.MICROSECONDS.convert(postfixTime, TimeUnit.NANOSECONDS);
		}

		System.out.printf("""
				Combinations: %d | Time: %d
				Permutations: %d | Time: %d
				Postfix: %d | Time: %d
				Solutions: %d
				""",
				numCombinations, combTime,
				numPermutations, permTime,
				numPostfix, postfixTime,
				numSolutions
				);

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
