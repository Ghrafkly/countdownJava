package org.countdownJava.Archive.Bytes;

import java.util.HashMap;
import java.util.Map;

public class Evaluate {
	public static int invalidEquations = 0, validEquations = 0, invalidSolutions = 0, validSolutions = 0;
	public static Map<Operations, Integer> intermidiarySolutions = new HashMap<>();

	public void evaluate(byte[][] postfixArray) {
		for (byte[] postfix : postfixArray) {
			boolean valid = true;
			int[] stack = new int[11];
			int stackIndex = 0;

			for (byte token : postfix) {
				int result = 0;
				boolean unique = true;

				if (!(tokenIsOp(token))) {
					stack[stackIndex++] = token;
				} else {
					int b = stack[--stackIndex], a = stack[--stackIndex];

					// deal with commutative operators i.e. a + b = b + a
					if ((token == -1 || token == -3) && a < b) {
						int temp = a;
						a = b;
						b = temp;
					}

					Operations op = new Operations(a, b, token);

					// check if a and b are in intermidiarySolutions
					if (intermidiarySolutions.containsKey(op)) {
						result = intermidiarySolutions.get(op);
						unique = false;
					} else {
						switch (token) {
							case -1 -> result = a + b;
							case -3 -> result = a * b;

							case -2 -> {
								// If result is negative, equation is not valid
								if (a > b) result = a - b;
								else valid = false;
							}
							case -4 -> {
								// If result is not an integer, equation is not valid
								if (a % b == 0) result = a / b;
								else valid = false;
							}
						}
						// if result becomes invalid, break loop
						if (!valid) break;
					}

					stack[stackIndex++] = result;
					intermidiarySolutions.put(op, result);

					// if result is a three-digit number, and is a unique solution, add to map
					if (unique) {
						if (result >= 101 && result <= 999) {
							validSolutions++;
							Runner.solutions.put(result, Runner.solutions.getOrDefault(result, 0L) + 1);
						} else {
							invalidSolutions++;
						}
					}
				}

			}

			// if equation is valid, increment validEquations
			if (valid) validEquations++;
			else invalidEquations++;
		}
	}

//	public void evaluate(byte[][] postfixArray) {
//		for (byte[] postfix : postfixArray) {
//			boolean valid = true;
//			int[] stack = new int[6];
//			int stackIndex = 0;
//
//			System.out.println(Arrays.toString(postfix));
//			for (byte token : postfix) {
//				int result = 0;
//
//				if (!(tokenIsOp(token))) {
//					stack[stackIndex++] = token;
//				} else {
//					int b = stack[--stackIndex], a = stack[--stackIndex];
//
//					// deal with commutative operators i.e. a + b = b + a
//					if ((token == -1 || token == -3) && a < b) {
//						int temp = a;
//						a = b;
//						b = temp;
//					}
//
//					Operations op = new Operations(a, b, token);
//
//					// check if a and b are in intermidiarySolutions
//					if (intermidiarySolutions.containsKey(op)) {
//						result = intermidiarySolutions.get(op);
//						System.out.printf("Found in intermidiarySolutions: %d %s %d = %d%n", op.a(), op.op(), op.b(), intermidiarySolutions.get(op));
//					} else {
//						switch (token) {
//							case -1 -> {
//								result = a + b;
//								System.out.printf("%d + %d = %d%n", a, b, result);
//							}
//							case -3 -> {
//								result = a * b;
//								System.out.printf("%d * %d = %d%n", a, b, result);
//							}
//
//							case -2 -> {
//								// If result is negative, equation is not valid
//								if (a > b) {
//									result = a - b;
//									System.out.printf("%d - %d = %d%n", a, b, result);
//								}
//								else valid = false;
//							}
//							case -4 -> {
//								// If result is not an integer, equation is not valid
//								if (a % b == 0) {
//									result = a / b;
//									System.out.printf("%d / %d = %d%n", a, b, result);
//								}
//								else valid = false;
//							}
//						}
//						// if result becomes invalid, break loop
//						if (!valid) {
//							System.out.printf("Invalid: %d %s %d = %d%n", a, op.op(), b, result);
//							break;
//						}
//					}
//
//					stack[stackIndex++] = result;
//					intermidiarySolutions.put(op, result);
//
//					// if result is a three-digit number, add to solutions map
//					if (result >= 101 && result <= 999) {
//						System.out.println("Valid solution: " + result);
//						validSolutions++;
//						Runner.solutions.put(result, Runner.solutions.getOrDefault(result, 0L) + 1);
//					} else {
//						invalidSolutions++;
//					}
//				}
//			}
//
//			// if equation is valid, increment validEquations
//			if (valid) validEquations++;
//			else invalidEquations++;
//		}
//	}

	public boolean tokenIsOp(byte token) {
		return token == -1 || token == -2 || token == -3 || token == -4;
	}
}
