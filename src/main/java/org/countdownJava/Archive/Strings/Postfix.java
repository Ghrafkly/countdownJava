package org.countdownJava.Archive.Strings;

import java.util.Arrays;

public class Postfix {
	private final String[] operators = {"+", "-", "*", "/"};
	private String[][] postfixArray;
	private String[] list;
	private int currentIndex = 0, listIndex = 0, postfixIndex = 0;

	public Postfix(String[] permutations) {
		this.postfixArray = new String[43008][11];
		this.list = permutations;
	}

	public String[][] generate(String[] current, int opsNeeded) {
		if (opsNeeded == 0 && listIndex == list.length) {
			String[] copy = Arrays.copyOf(current, current.length);
			postfixArray[postfixIndex++] = copy;
		}

		if (opsNeeded > 0) {
			for (String operator : operators) {
				current[currentIndex++] = operator;
				generate(current, opsNeeded - 1);
				current[--currentIndex] = null;
			}
		}

		if (listIndex < list.length) {
			String hold = list[listIndex++];
			current[currentIndex++] = hold;
			generate(current, opsNeeded + 1);
			current[--currentIndex] = null;
			list[--listIndex] = hold;
		}

		return postfixArray;
	}
}
