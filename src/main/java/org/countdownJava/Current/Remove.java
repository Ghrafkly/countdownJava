package org.countdownJava.Current;

import java.util.Arrays;

public class Remove {
	// a = 1, b = 2, c = 3, d = 4, e = 5, f = 6, g = 7, h = 8, i = 9, j = 10, k = 25, l = 50, m = 75, n = 100, o = +, p = -, q = *, r = /
	public boolean removeCheck(char[] postfix) {
		boolean one, two;

		one = minusDivide(postfix);
		two = hundredMultiply(postfix);

		return one && two;
	}

	public boolean minusDivide(char[] postfix) {
		// if contains only minus and/or divide
		int opCheck = 0;
		for (char token : postfix) {
			if (token == 'p') {
				opCheck++;
			}
			if (token == 'r') {
				opCheck++;
			}
		}

		return opCheck < 5;
	}

	public boolean hundredMultiply(char[] postfix) {
		// if contains 100 and all operators are * return false
		int opCheck = 0;
		boolean containsHundred = false;
		for (char token : postfix) {
			if (token == 'n') {
				containsHundred = true;
			}
			if (token == 'q') {
				opCheck++;
			}
		}

		return !(containsHundred && opCheck == 5);
	}
}
