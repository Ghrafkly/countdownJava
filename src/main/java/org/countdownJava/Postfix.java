package org.countdownJava;

import java.util.*;

public class Postfix {

    private final List<String> operators;
    private Set<List<String>> postfixSet;
    private List<String> list;

    public Postfix(List<String> numbers) {
        this.operators = new ArrayList<>(List.of("+", "-", "*", "/"));
        this.postfixSet = new HashSet<>();
        this.list = new ArrayList<>(numbers);
        Collections.reverse(list);
    }

    public List<List<String>> generate(List<String> current, int opsNeeded) {
        if (opsNeeded == 0 && list.size() == 0) {
            List<String> copy = new ArrayList<>(current);
            postfixSet.add(copy);
        }

        if (opsNeeded > 0) {
            for (String operator : operators) {
                current.add(operator);
                generate(current, opsNeeded - 1);
                current.remove(current.size() - 1);
            }
        }

        if (list.size() > 0) {
            String hold = list.remove(list.size() - 1);
            current.add(hold);
            generate(current, opsNeeded + 1);
            current.remove(current.size() - 1);
            list.add(hold);
        }

        return new ArrayList<>(postfixSet);
    }
}
