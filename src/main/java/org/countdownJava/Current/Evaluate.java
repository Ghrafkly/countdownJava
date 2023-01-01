package org.countdownJava.Current;

public class Evaluate {
    public static int invalidEquations = 0, validEquations = 0, invalidSolutions = 0, validSolutions = 0;

    public void evaluate(String[][] postfixArray) {
        int[] resultList = new int[200000];
        int index = 0;

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
                    int b = stack[--stackIndex], a = stack[--stackIndex];
                    switch (token) {
                        case "+" -> stack[stackIndex++] = a + b;
                        case "*" -> stack[stackIndex++] = a * b;

                        case "-" -> {
                            // If result is negative, equation is not valid
                            if (a > b) stack[stackIndex++] = a - b;
                            else valid = false;
                        }
                        case "/" -> {
                            // If result is not an integer, equation is not valid
                            if (a % b == 0) stack[stackIndex++] = a / b;
                            else valid = false;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + token);
                    }
                }

                if (stackIndex == 1 && valid) {
                    int result = stack[0];
                    if (result >= 101 && result <= 999) {
                        resultList[index++] = result;
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

        for (int result : resultList) {
            if (result != 0) {
                Runner.solutions.put(result, Runner.solutions.getOrDefault(result, 0) + 1);
            }
        }
    }
}
