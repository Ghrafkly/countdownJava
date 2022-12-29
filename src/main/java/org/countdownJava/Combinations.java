package org.countdownJava;

import java.util.*;

public class Combinations {
    // generate all combinations of R items from N items
    public List<List<String>> generate(ArrayList<String> numbers, int N, int R) {
        Set<List<String>> combinations = new HashSet<>();
        ArrayList<String> combination = new ArrayList<>();
        combinations(numbers, combinations, combination, 0, 0, N, R);

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
}
