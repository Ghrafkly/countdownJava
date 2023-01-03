package org.countdownJava.Test;

public class Test {
	public static void main(String[] args) {
		// utf-8
		System.out.println('\0' + 10); // = 200

		char zero = '\u0000';
		char one = '\u0001';
		char two = '\u0002';
		char three = '\u0003';
		char four = '\u0004';
		char five = '\u0005';
		char six = '\u0006';
		char seven = '\u0007';
		char eight = '\u0008';
		char nine = '\u0009';
		/*
		char ten = '\u000A';
		*/


		char twenty = '\u0014';
		char twentyFive = '\u0019';
		char fifty = '\u0032';
		char seventyFive = '\u004B';
		char hundred = '\u0064';

//		1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100
		// convert the above to a char array, replace 10 with 0. Use char values, not variables
		char[] chars = {one, one, two, two, three, three, four, four, five, five, six, six, seven, seven, eight, eight, nine, nine, zero, zero, twentyFive, fifty, seventyFive, hundred};
		char[] another = {'\u0001', '\u0001', '\u0002', '\u0002', '\u0003', '\u0003', '\u0004', '\u0004', '\u0005', '\u0005', '\u0006', '\u0006', '\u0007', '\u0007', '\u0008', '\u0008', '\u0009', '\u0009', '\u0000', '\u0000', '\u0019', '\u0032', '\u004B', '\u0064'};


	}
}
