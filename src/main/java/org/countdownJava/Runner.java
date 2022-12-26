package org.countdownJava;

import java.util.*;

public class Runner {
	public static int R;
	public static ArrayList<String> numbers;
	private Combinations combinations;
	private Permutations permutations;
	private List<List<String>> combinationsSet, permutationsSet, postfixSet;
	private Map<List<String>, List<List<String>>> combinationsPermutationsMap = new HashMap<>(), permutationsPostfixMap = new HashMap<>();
	private int numberOfCombinations, numberOfPermutations, numberOfPostfix;

	public void combinations() {
		combinations = new Combinations(numbers);
		combinationsSet = combinations.generate(numbers.size(), R);

		numberOfCombinations = combinationsSet.size();
	}

	public void permutations() {
		permutations = new Permutations();

		for (List<String> combination : combinationsSet) {
			permutationsSet = permutations.generate(combination);
			combinationsPermutationsMap.put(combination, permutationsSet);

			numberOfPermutations += permutationsSet.size();
		}
	}

	public void postfix() {
		for (Map.Entry<List<String>, List<List<String>>> entry : combinationsPermutationsMap.entrySet()) {
			for (List<String> permutation : entry.getValue()) {
				System.out.println("Hello");
				Postfix postfix = new Postfix(permutation);
				postfixSet = postfix.generate(new ArrayList<>(), -1);
				permutationsPostfixMap.put(permutation, postfixSet);

				numberOfPostfix += postfixSet.size();

				System.out.println(numberOfPostfix);
			}

			// Evaluate the postfix expressions, then clear them to save memory
//			evaluate(permutationsPostfixMap);
			permutationsPostfixMap.clear();
		}
	}

	private void evaluate(Map<List<String>, List<List<String>>> map) {
		for (Map.Entry<List<String>, List<List<String>>> entry : map.entrySet()) {
			Evaluate evaluate = new Evaluate(entry.getValue());
			evaluate.evaluate();
		}
	}

	public void start() {
		combinations();
		permutations();
//		postfix(); // This also evaluates the postfix expressions
		printCounts();
//		printMaps();
		printSolutions();
	}

	public static void main(String[] args) {
		Runner runner = new Runner();

//		numbers =  new ArrayList<>(Arrays.asList("1", "2", "3"));
//		numbers = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "4"));
        numbers = new ArrayList<>(Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8", "9", "9", "10", "10", "25", "50", "75", "100"));

		R = 6;

		runner.start();

		System.out.println("Valid Equations: " + Evaluate.validEquations);
		System.out.println("Invalid Equations: " + Evaluate.invalidEquations);
	}

	public void printCounts() {
		System.out.println("Number of combinations: " + numberOfCombinations);
		System.out.println("Number of permutations: " + numberOfPermutations);
		System.out.println("Number of postfix: " + numberOfPostfix);
	}

	public void printMaps() {
		System.out.println("Combinations :: Permutations");
		for (Map.Entry<List<String>, List<List<String>>> entry : combinationsPermutationsMap.entrySet()) {
			System.out.println(entry.getKey() + " :: " + entry.getValue());
		}

		System.out.println();

		System.out.println("Permutations :: Postfix");
		for (Map.Entry<List<String>, List<List<String>>> entry : permutationsPostfixMap.entrySet()) {
			System.out.println(entry.getKey() + " :: " + entry.getValue());
			System.out.println("...");
		}

	}

	public void printSolutions() {
		for (Map.Entry<Integer, Integer> entry : Evaluate.solutions.entrySet()) {
			System.out.printf("%4d = %4d%n", entry.getKey(), entry.getValue());
		}
	}
}

