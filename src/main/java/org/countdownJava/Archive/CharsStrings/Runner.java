package org.countdownJava.Archive.CharsStrings;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Runner {
	public static int R;
	public static ArrayList<String> numbers;
	public static char[] operators = {'+', '-', '*', '/'};
	public static Map<Integer, Long> solutions = new HashMap<>();
	public static int invalid = 0;
	private List<List<String>> combinationsSet, permutationsSet;
	private int numberOfCombinations, numberOfPermutations, numberOfPostfix;
	private final Map<List<String>, List<List<String>>> combinationsPermutationsMap = new HashMap<>();
	private final Map<List<String>, char[][]> permutationsPostfixMap = new HashMap<>();
	private Decoder decoder = new Decoder();

	public void combinations() {
		Combinations combinations = new Combinations();
		System.out.println(numbers);
		combinationsSet = combinations.generate(numbers, numbers.size(), R);
		System.out.println(combinationsSet.size());

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
		for (Map.Entry<List<String>, List<List<String>>> entry : combinationsPermutationsMap.entrySet()) {
			for (List<String> permutation : entry.getValue()) {
				char[] permutationArray = new char[permutation.size()];
				for (int i = 0; i < permutation.size(); i++) {
					permutationArray[i] = permutation.get(i).charAt(0);
				}

				Postfix postfix = new Postfix(operators, permutationArray);

				char[][] postfixArray = postfix.generate(new char[11], -1);


				// print postfixArray.length - nulls
//				System.out.println(IntStream.range(0, postfixArray.length)
//						.filter(i -> postfixArray[i][0] != '\0')
//						.count());;

				permutationsPostfixMap.put(permutation, postfixArray);

				numberOfPostfix += postfixArray.length;
			}

			evaluate(permutationsPostfixMap);
			permutationsPostfixMap.clear();
		}
	}

	public void evaluate(Map<List<String>, char[][]> map) {
		Evaluate evaluate = new Evaluate();
		int index = 0;
		for (Map.Entry<List<String>, char[][]> entry : map.entrySet()) {
			String[][] postfixArray = new String[41664][11];
			for (char[] postfix : entry.getValue()) {
				postfixArray[index++] = decoder.decodePostfix(postfix);
			}
			evaluate.evaluate(postfixArray);
			index = 0;
		}
	}

	public void start() {
		long startTime;

		System.out.printf("Numbers used: %s%n", numbers);
		System.out.printf("Operators used: %s%n", Arrays.toString(operators));
		System.out.printf("%d numbers, %d chosen at a time%n%n", numbers.size(), R);

		encode();

		System.out.printf("Numbers encoded: %s%n", numbers);
		System.out.printf("Operators encoded: %s%n%n", Arrays.toString(operators));

		startTime = System.currentTimeMillis();
		combinations();
		System.out.printf("Combinations: %d in %s%n", numberOfCombinations, time(startTime));

		startTime = System.currentTimeMillis();
		permutations();
		System.out.printf("Permutations: %d in %s%n", numberOfPermutations, time(startTime));

		startTime = System.currentTimeMillis();
		postfix();
		System.out.printf("Postfix: %d in %s%n", numberOfPostfix, time(startTime));

		printCounts();
		printSolutions();

		System.out.println(Evaluate.intermidiarySolutions.size());
	}
	public static void main(String[] args) {
		Runner runner = new Runner();

		IntStream.range(101, 1000).forEach(i -> solutions.put(i, 0L));

		// 13243 combinations
		// 5281560 permutations
		// 41580 postfix per permutation


//		numbers = new ArrayList<>(Arrays.asList("10", "10", "9", "9", "8", "8"));
		numbers = new ArrayList<>(Arrays.asList("10", "25", "75", "100", "9", "8"));
//        numbers = new ArrayList<>(Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8", "9", "9", "10", "10", "25", "50", "75", "100"));
		// just the numbers 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100

		R = 6;

		runner.start();

		WriteToFile writer = new WriteToFile();
		writer.write(solutions);
	}

	public void encode() {
		Encoder encoder = new Encoder();
		numbers = encoder.encodeNumbers(numbers);
		operators = encoder.encodeOperators(operators);
	}

	public void printCounts() {
		System.out.printf("Number of combinations: %d%n", numberOfCombinations);
		System.out.printf("Number of permutations: %d%n", numberOfPermutations);
		System.out.printf("Number of postfix: %d%n", numberOfPostfix);
	}

	public void printSolutions() {
		System.out.printf("Valid Equations: %d%n", Evaluate.validEquations);
		System.out.printf("Valid Solutions: %d%n", Evaluate.validSolutions);
		System.out.printf("Invalid Equations: %d%n", Evaluate.invalidEquations);
		System.out.printf("Invalid Solutions: %d%n", Evaluate.invalidSolutions);
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
}
