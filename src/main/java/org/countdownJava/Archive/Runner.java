package org.countdownJava.Archive;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Runner {
	public static int R;
	public static ArrayList<String> numbers;
	public static Map<Integer, Integer> solutions = new HashMap<>();
	public static List<Integer> avgRunTime = new ArrayList<>();
	private List<List<String>> combinationsSet, permutationsSet;
	private String[][] postfixArray = new String[43008][11];
	private final Map<List<String>, List<List<String>>> combinationsPermutationsMap = new HashMap<>();
	private final Map<List<String>, String[][]> permutationsPostfixMap = new HashMap<>();
	private int numberOfCombinations, numberOfPermutations, numberOfPostfix;

	public void combinations() {
		Combinations combinations = new Combinations();
		combinationsSet = combinations.generate(numbers, numbers.size(), R);
		numberOfCombinations = combinationsSet.size();
	}

	public void permutations() {
		Permutations permutations = new Permutations();

		for (List<String> combination : combinationsSet) {
			permutationsSet = permutations.generate(combination);
			combinationsPermutationsMap.put(combination, permutationsSet);

			numberOfPermutations += permutationsSet.size();
		}
	}

	public void postfix() {
		avgRunTime.clear(); // Time Tracker
		for (Map.Entry<List<String>, List<List<String>>> entry : combinationsPermutationsMap.entrySet()) {
			for (List<String> permutation : entry.getValue()) {
				long start = System.currentTimeMillis(); // Time Tracker

				String[] permutationArray = permutation.toArray(new String[6]);
				Postfix postfix = new Postfix(permutationArray);

				postfixArray = postfix.generate(new String[11], -1);
				permutationsPostfixMap.put(permutation, postfixArray);

				numberOfPostfix += postfixArray.length;

				avgRunTime.add((int) (System.currentTimeMillis() - start)); // Time Tracker
			}

			// Evaluate Postfix, then clear the map to save memory
			evaluate(permutationsPostfixMap);
			permutationsPostfixMap.clear();
		}
	}

	private void removeDuds(Map<List<String>, String[][]> map) {
		// remove if only contains -
		// mirror array with bits for true and false
	}

	private void evaluate(Map<List<String>, String[][]> map) {
		for (Map.Entry<List<String>, String[][]> entry : map.entrySet()) {
			Evaluate evaluate = new Evaluate(entry.getValue());
			evaluate.evaluate();
		}
	}

	public void start() {
		long start;

		System.out.printf("Numbers used: %s%n", numbers);
		System.out.printf("%d numbers, %d chosen at a time%n%n", numbers.size(), R);

		start = System.currentTimeMillis();
		combinations();
		System.out.printf("Combinations: %d in %s%n", numberOfCombinations, time(start));

		start = System.currentTimeMillis();
		permutations();
		System.out.printf("Permutations: %d in %s%n", numberOfPermutations, time(start));


		start = System.currentTimeMillis();
		postfix();
		System.out.printf("Postfix & Evaluation: %d in %s%n", numberOfPostfix, time(start));

//		start = System.currentTimeMillis();
//		evaluate(permutationsPostfixMap);
//		System.out.printf("Evaluate: %d in %s%n", numberOfPostfix, time(start));

		printCounts();
//		printMaps();
		printSolutions();
//		writeToFile();
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		IntStream.range(101, 1000).forEach(i -> solutions.put(i, 0));

		numbers = new ArrayList<>(Arrays.asList("10", "25", "75", "100", "9", "8"));
//        numbers = new ArrayList<>(Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8", "9", "9", "10", "10", "25", "50", "75", "100"));

		R = 6;

		runner.start();
	}

	public void printCounts() {
		System.out.printf("Number of combinations: %d%n", numberOfCombinations);
		System.out.printf("Number of permutations: %d%n", numberOfPermutations);
		System.out.printf("Number of postfix: %d%n", numberOfPostfix);
	}

	public void printMaps() {
		System.out.println("Combinations :: Permutations");
		for (Map.Entry<List<String>, List<List<String>>> entry : combinationsPermutationsMap.entrySet()) {
			System.out.printf("%s :: %s%n", entry.getKey(), entry.getValue());
		}

		System.out.println();

		System.out.println("Permutations :: Postfix");
		for (Map.Entry<List<String>, String[][]> entry : permutationsPostfixMap.entrySet()) {
			System.out.printf("%s :: %s%n", entry.getKey(), Arrays.deepToString(entry.getValue()));
			System.out.println("...");
		}

	}

	public void printSolutions() {
		System.out.printf("Valid Equations: %d%n", Evaluate.validEquations);
		System.out.printf("Valid Solutions: %d%n", Evaluate.validSolutions);
		System.out.printf("Invalid Equations: %d%n", Evaluate.invalidEquations);
		System.out.printf("Invalid Solutions: %d%n", Evaluate.invalidSolutions);

		int valueCheck = 0;
		for (Map.Entry<Integer, Integer> entry : solutions.entrySet()) {
			if (entry.getValue() != 0) {
				valueCheck += entry.getValue();
			}
		}

		System.out.printf("Value Check: %d%n", valueCheck);

//		for (Map.Entry<Integer, Integer> entry : solutions.entrySet()) {
//			if (entry.getValue() != 0) {
//				System.out.printf("%d: %d%n", entry.getKey(), entry.getValue());
//			}
//		}
	}

	private String time(long start) {
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

	public void writeToFile() {
		File file = new File("src/main/java/org/countdownJava/solutions.txt");
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (Map.Entry<Integer, Integer> entry : solutions.entrySet()) {
				writer.write("%d = %d".formatted(entry.getKey(), entry.getValue()));
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				assert writer != null;
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

