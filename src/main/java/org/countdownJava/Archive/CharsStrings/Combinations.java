package org.countdownJava.Archive.CharsStrings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combinations {
	public List<List<String>> generate(ArrayList<String> numbers, int N, int R) {
		System.out.println(numbers);
		Set<List<String>> combinations = new HashSet<>();
		ArrayList<String> combination = new ArrayList<>();
		combinations(numbers, combinations, combination, 0, 0, N, R);

		System.out.println(combinations.size());
		return new ArrayList<>(combinations);
	}

	private void combinations(ArrayList<String> numbers, Set<List<String>> combinations, ArrayList<String> combination, int index, int start, int N, int R) {
		if (index == R) {
			combinations.add(List.of(combination.toArray(new String[0])));
			return;
		}

		for (int i = start; i < N; i++) {
			combination.add(numbers.get(i));
			combinations(numbers, combinations, combination, index + 1, i + 1, N, R);
			combination.remove(index);
		}
	}

	public static void main(String[] args) {
		int[] nums = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};
		ArrayList<String> numbers = new ArrayList<>();

		for (int num : nums) {
			numbers.add(String.valueOf(num));
		}

		Combinations combinations = new Combinations();
		combinations.generate(numbers, numbers.size(), 6);

	}
}
