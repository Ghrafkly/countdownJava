package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;

public class Postfix {
	private final int[] operators = {-1, -2, -3, -4};
	private int currentIndex = 0, listIndex = 0, postfixIndex = 0;
	private final Map<int[], int[][]> mapPermutationsPostfix = new HashMap<>();

	public void postfix(Map<int[], int[][]> mapCombinationsPermutations) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(12);
		Map<int[], Future<int[][]>> futures = new HashMap<>();

		for (Map.Entry<int[], int[][]> entry : mapCombinationsPermutations.entrySet()) {
			for (int[] permutation : entry.getValue()) {
				futures.put(permutation, executor.submit(() -> generatePostfix(permutation, new int[43008][5], new int[5], -1)));
			}
		}

		for (int[] permutation : futures.keySet()) {
			List<int[]> hold = new ArrayList<>();
			// culls out the null arrays
			for (int[] postfix : futures.get(permutation).get()) {
				if (postfix[0] != 0) {
					hold.add(postfix);
				}
			}

			mapPermutationsPostfix.put(permutation, hold.toArray(new int[0][0]));
		}

		executor.shutdownNow();
	}

	public int[][] generatePostfix(int[] permutation, int[][] postfixArray, int[] current, int opsNeeded) {
		try {
			if (opsNeeded == 0 && listIndex == permutation.length) {
				postfixArray[postfixIndex++] = Arrays.copyOf(current, current.length);
			}

			if (opsNeeded > 0) {
				for (int operator : operators) {
					current[currentIndex++] = operator;
					generatePostfix(permutation, postfixArray, current, opsNeeded - 1);
					currentIndex--;
				}
			}

			if (listIndex < permutation.length) {
				current[currentIndex++] = permutation[listIndex++];
				generatePostfix(permutation, postfixArray, current, opsNeeded + 1);
				currentIndex--;
				listIndex--;
			}
		} catch (Exception e) {
			System.out.println(Arrays.toString(permutation));
		}

		return postfixArray;

	}

	public Map<int[], int[][]> getMapPermutationsPostfix() {
		return mapPermutationsPostfix;
	}

	public static void main(String[] args) {
		System.out.println(672-576);
		System.out.println(576-480);
	}
}
