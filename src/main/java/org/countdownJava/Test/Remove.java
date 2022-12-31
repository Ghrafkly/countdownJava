package org.countdownJava.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Remove {
//	public void StringListStream() {
//		ArrayList<String> strList = new ArrayList<>(Arrays.asList("100", "10", "25", "9", "8", "-", "75", "-", "-", "-", "-"));
//
//		int opCheck = (int) strList.stream().filter(token -> token.equals("-") || token.equals("/")).count();
//	}
//
//	public void IntegerListStream() {
//		ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(100, 10, 25, 9, 8, 12, 75, 12, 12, 12, 12));
//
//		int opCheck = (int) intList.stream().filter(token -> token == 12 || token == 14).count();
//	}
//
//	public void CharListStream() {
//		ArrayList<Character> charList = new ArrayList<>(Arrays.asList('n', 'j', 'k', 'i', 'h', 'p', 'm', 'p', 'p', 'p', 'p'));
//
//		int opCheck = (int) charList.stream().filter(token -> token == 'p' || token == 'r').count();
//	}
//
//	public void StringArrayStream() {
//		String[] strArr = {"100", "10", "25", "9", "8", "-", "75", "-", "-", "-", "-"};
//
//		int opCheck = (int) Arrays.stream(strArr).filter(token -> token.equals("-") || token.equals("/")).count();
//	}
//
//	public void IntegerArrayStream() {
//		int[] intArr = {100, 10, 25, 9, 8, 12, 75, 12, 12, 12, 12};
//
//		int opCheck = (int) Arrays.stream(intArr).filter(token -> token == 12 || token == 14).count();
//	}
//
//	public void StringListLoop() {
//		ArrayList<String> strList = new ArrayList<>(Arrays.asList("100", "10", "25", "9", "8", "-", "75", "-", "-", "-", "-"));
//
//		int opCheck = 0;
//		for (String token : strList) {
//			if (token.equals("-") || token.equals("/")) {
//				opCheck++;
//			}
//		}
//	}
//
//	public void IntegerListLoop() {
//		ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(100, 10, 25, 9, 8, 12, 75, 12, 12, 12, 12));
//
//		int opCheck = 0;
//		for (Integer token : intList) {
//			if (token == 12 || token == 14) {
//				opCheck++;
//			}
//		}
//	}

	public void CharListLoop() {
		ArrayList<Character> charList = new ArrayList<>(Arrays.asList('n', 'j', 'k', 'i', 'h', 'p', 'm', 'p', 'p', 'r', 'r'));

		int opCheck = 0;
		for (Character token : charList) {
			if (token == 'p') {
				opCheck++;
			}
			if (token == 'r') {
				opCheck++;
			}
		}
	}

	public void StringArrayLoop() {
		String[] strArr = {"100", "10", "25", "9", "8", "-", "75", "-", "-", "/", "/"};

		int opCheck = 0;
		for (String token : strArr) {
			if (token.equals("-")) {
				opCheck++;
			}
			if (token.equals("/")) {
				opCheck++;
			}
		}
	}

	public void IntegerArrayLoop() {
		int[] intArr = {100, 10, 25, 9, 8, 12, 75, 12, 12, 14, 14};

		int opCheck = 0;
		for (int token : intArr) {
			if (token == 12) {
				opCheck++;
			}
			if (token == 14) {
				opCheck++;
			}
		}
	}

	public void CharArrayLoop() {
		char[] chArr = {'n', 'j', 'k', 'i', 'h', 'p', 'm', 'p', 'p', 'r', 'r'};

		int opCheck = 0;
		for (char token : chArr) {
			if (token == 'p') {
				opCheck++;
			}
			if (token == 'r') {
				opCheck++;
			}
		}
	}

	public void StrLoop() {
		String str = "njkihpmpppp";

		int opCheck = 0;
		for (char token : str.toCharArray()) {
			if (token == 'p') {
				opCheck++;
			}
			if (token == 'r') {
				opCheck++;
			}
		}
	}

	public static void main(String[] args) {
		Remove remove = new Remove();
		long startTime;
		long endTime;
		// one billion
		long runs = 1000000000;
		Map<String, Long> times = new HashMap<>();

		// String List Stream
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.StringListStream();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("String List Stream", endTime - startTime);
//		System.out.println("String List Stream: " + (endTime - startTime));
//
//		// Integer List Stream
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.IntegerListStream();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("Integer List Stream", endTime - startTime);
//		System.out.println("Integer List Stream: " + (endTime - startTime));
//
//		// Char List Stream
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.CharListStream();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("Char List Stream", endTime - startTime);
//		System.out.println("Char List Stream: " + (endTime - startTime));
//
//		// String Array Stream
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.StringArrayStream();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("String Array Stream", endTime - startTime);
//		System.out.println("String Array Stream: " + (endTime - startTime));
//
//		// Integer Array Stream
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.IntegerArrayStream();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("Integer Array Stream", endTime - startTime);
//		System.out.println("Integer Array Stream: " + (endTime - startTime));
//
//		// String List Loop
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.StringListLoop();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("String List Loop", endTime - startTime);
//		System.out.println("String List Loop: " + (endTime - startTime));
//
//		// Integer List Loop
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < runs; i++) {
//			remove.IntegerListLoop();
//		}
//		endTime = System.currentTimeMillis();
//		times.put("Integer List Loop", endTime - startTime);
//		System.out.println("Integer List Loop: " + (endTime - startTime));

		// Char List Loop
		startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			remove.CharListLoop();
		}
		endTime = System.currentTimeMillis();
		times.put("Char List Loop", endTime - startTime);
		System.out.println("Char List Loop: " + (endTime - startTime));

		// String Array Loop
		startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			remove.StringArrayLoop();
		}
		endTime = System.currentTimeMillis();
		times.put("String Array Loop", endTime - startTime);
		System.out.println("String Array Loop: " + (endTime - startTime));

		// Integer Array Loop
		startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			remove.IntegerArrayLoop();
		}
		endTime = System.currentTimeMillis();
		times.put("Integer Array Loop", endTime - startTime);
		System.out.println("Integer Array Loop: " + (endTime - startTime));

		// Char Array Loop
		startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			remove.CharArrayLoop();
		}
		endTime = System.currentTimeMillis();
		times.put("Char Array Loop", endTime - startTime);
		System.out.println("Char Array Loop: " + (endTime - startTime));

		// String Loop
		startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			remove.StrLoop();
		}
		endTime = System.currentTimeMillis();
		times.put("String Loop", endTime - startTime);
		System.out.println("String Loop: " + (endTime - startTime));

		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");

		// print map
		for (Map.Entry<String, Long> entry : times.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + " ms");
		}

		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");

		// sort map by fastest to slowest
		Map<String, Long> sortedTimes = times.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		// print sorted map
		for (Map.Entry<String, Long> entry : sortedTimes.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + " ms");
		}
	}
}
