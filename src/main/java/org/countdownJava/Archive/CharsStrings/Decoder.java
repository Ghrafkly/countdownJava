package org.countdownJava.Archive.CharsStrings;

public class Decoder {
	// a = 1, b = 2, c = 3, d = 4, e = 5, f = 6, g = 7, h = 8, i = 9, j = 10, k = 25, l = 50, m = 75, n = 100, o = +, p = -, q = *, r = /
	public String[] decodePostfix(char[] postfix) {
		String[] decodedPostfix = new String[postfix.length];

		for (int i = 0; i < postfix.length; i++) {
			switch (postfix[i]) {
				case 'a' -> decodedPostfix[i] = "1";
				case 'b' -> decodedPostfix[i] = "2";
				case 'c' -> decodedPostfix[i] = "3";
				case 'd' -> decodedPostfix[i] = "4";
				case 'e' -> decodedPostfix[i] = "5";
				case 'f' -> decodedPostfix[i] = "6";
				case 'g' -> decodedPostfix[i] = "7";
				case 'h' -> decodedPostfix[i] = "8";
				case 'i' -> decodedPostfix[i] = "9";
				case 'j' -> decodedPostfix[i] = "10";
				case 'k' -> decodedPostfix[i] = "25";
				case 'l' -> decodedPostfix[i] = "50";
				case 'm' -> decodedPostfix[i] = "75";
				case 'n' -> decodedPostfix[i] = "100";
				case 'o' -> decodedPostfix[i] = "+";
				case 'p' -> decodedPostfix[i] = "-";
				case 'q' -> decodedPostfix[i] = "*";
				case 'r' -> decodedPostfix[i] = "/";
			}
		}

		return decodedPostfix;
	}
}

