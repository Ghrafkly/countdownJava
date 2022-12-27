package org.countdownJava;

import org.countdownJava.Database.DatabaseConnection;
import org.countdownJava.Database.ModifyDB;
import org.countdownJava.Math.Combinations;
import org.countdownJava.Math.Evaluate;
import org.countdownJava.Math.Permutations;
import org.countdownJava.Math.Postfix;

import java.sql.Connection;
import java.util.*;

public class Runner {
	public static int R;
	public static ArrayList<String> numbers;
	private List<List<String>> combinationsSet, permutationsSet, postfixSet;
	private final Map<List<String>, List<List<String>>> combinationsPermutationsMap = new HashMap<>(), permutationsPostfixMap = new HashMap<>();
	private int numberOfCombinations, numberOfPermutations, numberOfPostfix;

	private final ModifyDB modifyDB = new ModifyDB();

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

		modifyDB.addCP(combinationsPermutationsMap);
	}

	public void postfix() {
		for (Map.Entry<List<String>, List<List<String>>> entry : combinationsPermutationsMap.entrySet()) {
			for (List<String> permutation : entry.getValue()) {
				Postfix postfix = new Postfix(permutation);
				postfixSet = postfix.generate(new ArrayList<>(), -1);
				permutationsPostfixMap.put(permutation, postfixSet);

				numberOfPostfix += postfixSet.size();

				// Evaluate the postfix expressions, then clear them to save memory
//				evaluate(permutationsPostfixMap);
//				permutationsPostfixMap.clear();

//				System.out.println(Evaluate.validEquations);
//				for (Map.Entry<Integer, Integer> e : Evaluate.solutions.entrySet()) {
//					System.out.printf("%4d = %4d%n", e.getKey(), e.getValue());
//				}
			}


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
//		printCounts();
//		printMaps();
//		printSolutions();
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		ModifyDB db = new ModifyDB();

//		if (runner.resetCheck()) db.resetDB();

//		numbers =  new ArrayList<>(Arrays.asList("1", "2", "3"));
//		numbers = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "4"));
        numbers = new ArrayList<>(Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8", "9", "9", "10", "10", "25", "50", "75", "100"));

		R = 6;

		runner.start();

		System.out.println("Valid Equations: " + Evaluate.validEquations);
		System.out.println("Invalid Equations: " + Evaluate.invalidEquations);
	}

	public boolean resetCheck() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Reset DB [Y/N]");
		String response = sc.nextLine();

		return response.equals("y");
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

