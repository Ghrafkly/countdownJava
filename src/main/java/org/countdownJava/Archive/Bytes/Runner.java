package org.countdownJava.Archive.Bytes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Runner {
	public static Map<Integer, Long> solutions = new HashMap<>();
	public static int invalid = 0;

	private final byte[] operators = {-1, -2, -3, -4};
	private final byte[] numbers = {10, 25, 75, 100, 9, 8};
//	private final byte[] numbers = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};

	private final Map<List<Byte>, List<List<Byte>>> mapCombinationsPermutations = new HashMap<>();
	private final Map<List<Byte>, byte[][]> mapPermutationsPostfix = new HashMap<>();

	private List<List<Byte>> setCombinations, setPermutations;
	private int numCombinations, numPermutations;
	private long numPostfix;

	private void combinations() {
		Combinations combinations = new Combinations();

		// Convert byte[] to List<Byte>
		ArrayList<Byte> nums = new ArrayList<>();
		for (byte number : numbers) nums.add(number);

		setCombinations = combinations.generate(nums, numbers.length, 5);
		numCombinations = setCombinations.size();

	}

	private void permutations() {
		Permutations permutations = new Permutations();

		for (List<Byte> combination : setCombinations) {
			setPermutations = permutations.generate(combination);
			mapCombinationsPermutations.put(combination, setPermutations);

			numPermutations += setPermutations.size();
		}
	}

	private void postfix() {
		for (Map.Entry<List<Byte>, List<List<Byte>>> entry : mapCombinationsPermutations.entrySet()) {
			for (List<Byte> permutation : entry.getValue()) {
				// Convert List<Byte> to byte[]
				byte[] permutationArray = new byte[permutation.size()];
				for (int i = 0; i < permutation.size(); i++) permutationArray[i] = permutation.get(i);

				Postfix postfix = new Postfix(permutationArray, operators);

				byte[][] postfixArray = postfix.generate(new byte[11], -1);

				mapPermutationsPostfix.put(permutation, postfixArray);

				numPostfix += postfixArray.length;
			}

			// Evaluate Postfix, then clear the map to save memory
			evaluate(mapPermutationsPostfix);
			mapPermutationsPostfix.clear();

//			System.out.printf("Combinations left: %d\r", --numCombinations);
		}
	}

	private void evaluate(Map<List<Byte>, byte[][]> map) {
		Evaluate evaluate = new Evaluate();
		for (Map.Entry<List<Byte>, byte[][]> entry : map.entrySet()) {
			evaluate.evaluate(entry.getValue());
		}
//		Evaluate.intermidiarySolutions.clear();
	}

	private void run() {
		long startTime;

		startTime = System.currentTimeMillis();
		combinations();
		System.out.printf("Combinations: %d in %s%n", numCombinations, timer(startTime));

		startTime = System.currentTimeMillis();
		permutations();
		System.out.printf("Permutations: %d in %s%n", numPermutations, timer(startTime));

		startTime = System.currentTimeMillis();
		postfix();
		System.out.printf("Postfix: %d in %s%n", numPostfix, timer(startTime));

//		printCounts();
		printSolutions();
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.run();

		WriteToFile writeToFile = new WriteToFile();
		writeToFile.write(solutions);
	}

	private void printCounts() {
		System.out.printf("Number of combinations: %d%n", numCombinations);
		System.out.printf("Number of permutations: %d%n", numPermutations);
		System.out.printf("Number of postfix: %d%n", numPostfix);
	}

	private void printSolutions() {
		System.out.printf("Valid Equations: %d%n", Evaluate.validEquations);
		System.out.printf("Valid Solutions: %d%n", Evaluate.validSolutions);
		System.out.printf("Invalid Equations: %d%n", Evaluate.invalidEquations);
		System.out.printf("Invalid Solutions: %d%n", Evaluate.invalidSolutions);

		System.out.printf("Total Equations: %d%n", Evaluate.validEquations + Evaluate.invalidEquations);
		System.out.printf("Total Solutions: %d%n", Evaluate.validSolutions + Evaluate.invalidSolutions);

		System.out.println("Solutions:");
		for (Map.Entry<Integer, Long> entry : solutions.entrySet()) {
			System.out.printf("%d: %d%n", entry.getKey(), entry.getValue());
		}
	}

	private String timer(long start) {
		long end;
		String time;

		end = System.currentTimeMillis();
		time = String.format("%d min, %d sec, %d milli",
				TimeUnit.MILLISECONDS.toMinutes(end - start),
				TimeUnit.MILLISECONDS.toSeconds(end - start) -
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end - start)),
				TimeUnit.MILLISECONDS.toMillis(end - start) -
						TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(end - start)));
		return time;
	}

}
