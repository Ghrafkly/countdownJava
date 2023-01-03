package org.countdownJava.Archive.CharsStrings;

import java.util.ArrayList;
import java.util.Map;

public class Encoder {
	// a = 1, b = 2, c = 3, d = 4, e = 5, f = 6, g = 7, h = 8, i = 9, j = 10, k = 25, l = 50, m = 75, n = 100, o = +, p = -, q = *, r = /
	public ArrayList<String> encodeNumbers(ArrayList<String> numbers) {
		ArrayList<String> encodedNumbers = new ArrayList<>();

		for (String number : numbers) {
			switch (number) {
				case "1" -> encodedNumbers.add("a");
				case "2" -> encodedNumbers.add("b");
				case "3" -> encodedNumbers.add("c");
				case "4" -> encodedNumbers.add("d");
				case "5" -> encodedNumbers.add("e");
				case "6" -> encodedNumbers.add("f");
				case "7" -> encodedNumbers.add("g");
				case "8" -> encodedNumbers.add("h");
				case "9" -> encodedNumbers.add("i");
				case "10" -> encodedNumbers.add("j");
				case "25" -> encodedNumbers.add("k");
				case "50" -> encodedNumbers.add("l");
				case "75" -> encodedNumbers.add("m");
				case "100" -> encodedNumbers.add("n");
			}
		}

		return encodedNumbers;
	}

	public char[] encodeOperators(char[] operators) {
		char[] encodedOperators = new char[operators.length];

		for (int i = 0; i < operators.length; i++) {
			switch (operators[i]) {
				case '+' -> encodedOperators[i] = 'o';
				case '-' -> encodedOperators[i] = 'p';
				case '*' -> encodedOperators[i] = 'q';
				case '/' -> encodedOperators[i] = 'r';
			}
		}

		return encodedOperators;
	}
}
