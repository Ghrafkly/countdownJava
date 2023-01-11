package org.countdownJava.Current;

import java.util.Arrays;
import java.util.List;

public class PostfixGen {
	private final int[] operators = {-1, -2, -3, -4};
	private final int[] permutation;
	private int currentIndex = 0, listIndex = 0, postfixIndex = 0;

	public PostfixGen(List<Integer> permutation) {
		this.permutation = permutation.stream().mapToInt(i -> i).toArray();
	}

	public int[][] generatePostfix() {
		return generatePostfix(new int[43008][11], new int[11], -1);
	}

	public int[][] generatePostfix(int[][] postfixArray, int[] current, int opsNeeded) {
		if (opsNeeded == 0 && listIndex == permutation.length) {
			postfixArray[postfixIndex++] = Arrays.copyOf(current, current.length);
		}

		if (opsNeeded > 0) {
			for (int operator : operators) {
				current[currentIndex++] = operator;
				generatePostfix(postfixArray, current, opsNeeded - 1);
				currentIndex--;
			}
		}

		if (listIndex < permutation.length) {
			current[currentIndex++] = permutation[listIndex++];
			generatePostfix(postfixArray, current, opsNeeded + 1);
			currentIndex--;
			listIndex--;
		}

		return postfixArray;
	}
}
