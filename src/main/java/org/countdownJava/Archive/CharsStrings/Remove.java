package org.countdownJava.Archive.CharsStrings;

import java.util.Arrays;

public class Remove {
	// a = 1, b = 2, c = 3, d = 4, e = 5, f = 6, g = 7, h = 8, i = 9, j = 10, k = 25, l = 50, m = 75, n = 100, o = +, p = -, q = *, r = /
	public boolean removeCheck(char[] postfix) {
		int minusDivideCheck = 0, plusCheck = 0, multiplyCheck = 0;

		for (char token : postfix) {
			switch (token) {
				case 'o' -> plusCheck++;
				case 'q' -> multiplyCheck++;
				case 'p', 'r' -> minusDivideCheck++;
			}
		}

		return (plusCheck < 5 && minusDivideCheck < 5 && multiplyCheck < 5);
	}
}
