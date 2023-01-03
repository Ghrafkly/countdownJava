package org.countdownJava.Current;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combinations {
	public List<List<Byte>> generate(ArrayList<Byte> numbers, int N, int R) {
		Set<List<Byte>> combinations = new HashSet<>();
		ArrayList<Byte> combination = new ArrayList<>();
		combinations(numbers, combinations, combination, 0, 0, N, R);

		return new ArrayList<>(combinations);
	}

	private void combinations(ArrayList<Byte> numbers, Set<List<Byte>> combinations, ArrayList<Byte> combination, int index, int start, int N, int R) {
		if (index == R) {
			combinations.add(List.of(combination.toArray(new Byte[0])));
			return;
		}

		for (int i = start; i < N; i++) {
			combination.add(numbers.get(i));
			combinations(numbers, combinations, combination, index + 1, i + 1, N, R);
			combination.remove(index);
		}
	}
}
