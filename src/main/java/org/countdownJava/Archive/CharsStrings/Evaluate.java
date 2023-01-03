package org.countdownJava.Archive.CharsStrings;

import java.util.HashMap;
import java.util.Map;

public class Evaluate {
    public static int invalidEquations = 0, validEquations = 0, invalidSolutions = 0, validSolutions = 0;
    public static Map<String, Integer> intermidiarySolutions = new HashMap<>();

    public void evaluate(char[][] postfixArray) {
        for (char[] postfix : postfixArray) {
            if (postfix[0] == '\0') break;

            int[] stack = new int[11];
            int top = -1;
            int a, b, result = 0;
            boolean invalid = false;

            for (char token : postfix) {
                if (token == '\0') break;

                if (Character.isDigit(token)) {
                    stack[++top] = Character.getNumericValue(token);
                } else {
                    if (top < 1) break;

                    a = stack[top--];
                    b = stack[top--];

                    switch (token) {
                        case 'o' -> result = b + a;
                        case 'q' -> result = b * a;
                        case 'p' -> {
                            if (a < b) {
                                result = b - a;
                            } else invalid = true;

                        }
                        case 'r' -> {
                            if (a % b == 0) {
                                result = a / b;
                            } else invalid = true;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + token);
                    }

                    if (invalid) break;

                    stack[++top] = result;
                }
            }
        }
    }

    public void evaluate(String[][] postfixArray) {
        long start = System.currentTimeMillis(); // Time Tracker
        for (String[] postfix : postfixArray) {
            if (postfix[0] == null) break;

            boolean valid = true;
            int[] stack = new int[11];
            int stackIndex = 0;

            for (String token : postfix) {
                // Checks if the equation remains valid
                if (!valid) break;

                // Evaluates the token
                if (token.matches("[0-9]+")) {
                    stack[stackIndex++] = Integer.parseInt(token);
                } else {
                    int result = 0;
                    int b = stack[--stackIndex], a = stack[--stackIndex];

                    // deal with commutative operators i.e. a + b = b + a
                    if ((token.equals("+") || token.equals("*")) && a < b) {
                        int temp = a;
                        a = b;
                        b = temp;
                    }

                    // check if a and b are in intermidiarySolutions
                    if (intermidiarySolutions.containsKey(a + token + b)) {
                        stack[stackIndex++] = intermidiarySolutions.get(a + token + b);
                    } else {
                        switch (token) {
                            case "+" -> {
                                result = a + b;
                                stack[stackIndex++] = result;
                            }
                            case "*" -> {
                                result = a * b;
                                stack[stackIndex++] = result;
                            }

                            case "-" -> {
                                // If result is negative, equation is not valid
                                if (a > b) {
                                    result = a - b;
                                    stack[stackIndex++] = result;
                                } else valid = false;
                            }
                            case "/" -> {
                                // If result is not an integer, equation is not valid
                                if (a % b == 0) {
                                    result = a / b;
                                    stack[stackIndex++] = result;
                                } else valid = false;
                            }
                            default -> throw new IllegalStateException("Unexpected value: " + token);
                        }

                        // add to intermidiarySolutions if valid
                        if (valid && result > 0) {
                            intermidiarySolutions.put(a + token + b, result);
                        }
                    }
                }

                if (stackIndex == 1 && valid) {
                    int result = stack[0];
                    if (result >= 101 && result <= 999) {
                        Runner.solutions.put(result, Runner.solutions.getOrDefault(result, 0L) + 1);
                        validSolutions++;
                    } else {
                        invalidSolutions++;
                    }
                }
            }

            /*
             If equation is valid, add result to list
             Valid equations are the following
             */
            if (valid) validEquations++;
            else invalidEquations++;
        }
    }
}
